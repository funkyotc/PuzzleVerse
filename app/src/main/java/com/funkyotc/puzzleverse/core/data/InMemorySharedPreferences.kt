package com.funkyotc.puzzleverse.core.data

import android.content.SharedPreferences

class InMemorySharedPreferences : SharedPreferences {
    private val data = mutableMapOf<String, Any>()
    private val listeners = mutableListOf<SharedPreferences.OnSharedPreferenceChangeListener>()

    override fun getAll(): MutableMap<String, *> = data.toMutableMap()
    override fun getString(key: String?, defValue: String?): String? = (data[key] as? String) ?: defValue
    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        val raw = data[key] ?: return defValues
        return when (raw) {
            is Set<*> -> raw.filterIsInstance<String>().toMutableSet()
            is Collection<*> -> raw.filterIsInstance<String>().toMutableSet()
            else -> defValues
        }
    }
    override fun getInt(key: String?, defValue: Int): Int = (data[key] as? Int) ?: (data[key]?.toString()?.toIntOrNull()) ?: defValue
    override fun getLong(key: String?, defValue: Long): Long = (data[key] as? Long) ?: (data[key]?.toString()?.toLongOrNull()) ?: defValue
    override fun getFloat(key: String?, defValue: Float): Float = (data[key] as? Float) ?: (data[key]?.toString()?.toFloatOrNull()) ?: defValue
    override fun getBoolean(key: String?, defValue: Boolean): Boolean = (data[key] as? Boolean) ?: (data[key]?.toString()?.toBooleanStrictOrNull()) ?: defValue
    override fun contains(key: String?): Boolean = data.containsKey(key)
    override fun edit(): SharedPreferences.Editor = Editor()
    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        if (listener != null && !listeners.contains(listener)) listeners.add(listener)
    }
    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        listeners.remove(listener)
    }

    inner class Editor : SharedPreferences.Editor {
        private val temp = mutableMapOf<String, Any?>()
        private val removedKeys = mutableSetOf<String>()
        private var clearAll = false

        override fun putString(key: String?, value: String?): SharedPreferences.Editor {
            if (key != null) {
                if (value != null) temp[key] = value else removedKeys.add(key)
            }
            return this
        }
        override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor {
            if (key != null) {
                if (values != null) temp[key] = values.toSet() else removedKeys.add(key)
            }
            return this
        }
        override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
            if (key != null) temp[key] = value
            return this
        }
        override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
            if (key != null) temp[key] = value
            return this
        }
        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
            if (key != null) temp[key] = value
            return this
        }
        override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
            if (key != null) temp[key] = value
            return this
        }
        override fun remove(key: String?): SharedPreferences.Editor {
            if (key != null) {
                removedKeys.add(key)
                temp.remove(key)
            }
            return this
        }
        override fun clear(): SharedPreferences.Editor {
            clearAll = true
            return this
        }
        override fun commit(): Boolean {
            apply()
            return true
        }
        override fun apply() {
            val changedKeys = mutableListOf<String>()
            if (clearAll) {
                changedKeys.addAll(data.keys)
                data.clear()
                clearAll = false
            }
            for (k in removedKeys) {
                if (data.containsKey(k)) {
                    data.remove(k)
                    changedKeys.add(k)
                }
            }
            removedKeys.clear()
            for ((k, v) in temp) {
                if (v != null) {
                    data[k] = v
                    changedKeys.add(k)
                }
            }
            temp.clear()
            for (key in changedKeys) {
                for (listener in listeners.toList()) {
                    listener.onSharedPreferenceChanged(this@InMemorySharedPreferences, key)
                }
            }
        }
    }
}

