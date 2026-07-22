import os
import math
import struct
import wave
import random

SAMPLE_RATE = 44100

def create_wav(filename, samples):
    """Write mono 16-bit PCM samples to a WAV file."""
    os.makedirs(os.path.dirname(filename), exist_ok=True)
    with wave.open(filename, 'w') as wav_file:
        wav_file.setnchannels(1)  # Mono
        wav_file.setsampwidth(2)  # 16-bit
        wav_file.setframerate(SAMPLE_RATE)
        
        # Clamp and pack integer PCM samples (-32768 to 32767)
        packed_data = bytearray()
        for sample in samples:
            clamped = max(-1.0, min(1.0, sample))
            int_sample = int(clamped * 32767.0)
            packed_data.extend(struct.pack('<h', int_sample))
        
        wav_file.writeframes(packed_data)
    print(f"Generated {filename} ({len(samples)} samples, {len(samples)/SAMPLE_RATE:.3f}s)")

# Helper DSP synthesis functions
def exp_decay(t, duration, decay_rate=8.0):
    return math.exp(-decay_rate * (t / duration))

def sine_wave(freq, t):
    return math.sin(2.0 * math.pi * freq * t)

def square_wave(freq, t, duty=0.5):
    val = math.sin(2.0 * math.pi * freq * t)
    return 1.0 if val >= 0 else -1.0

def triangle_wave(freq, t):
    val = (2.0 * math.pi * freq * t) % (2.0 * math.pi)
    return 2.0 * abs(val / math.pi - 1.0) - 1.0

def noise_sample():
    return random.uniform(-1.0, 1.0)

# Sound Generators

def gen_ui_click(duration=0.035):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    freq = 1200.0
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-200.0 * t)
        val = sine_wave(freq, t) * env * 0.5
        samples.append(val)
    return samples

def gen_tile_place(duration=0.045):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-120.0 * t)
        body = sine_wave(750.0, t) * 0.6
        overtone = sine_wave(1500.0, t) * 0.25
        samples.append((body + overtone) * env * 0.6)
    return samples

def gen_block_drop(duration=0.075):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-60.0 * t)
        sub = sine_wave(90.0, t) * 0.5
        body = sine_wave(220.0, t) * 0.4
        transient = noise_sample() * math.exp(-300.0 * t) * 0.2
        samples.append((sub + body + transient) * env * 0.7)
    return samples

def gen_piece_slide(duration=0.090):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.sin(math.pi * (t / duration)) # Bell shape
        # Bandpassed noise simulation
        freq = 300.0 + (500.0 * (t / duration))
        val = (sine_wave(freq, t) * 0.4 + noise_sample() * 0.3) * env * 0.4
        samples.append(val)
    return samples

def gen_snap_connect(duration=0.045):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-150.0 * t)
        f1 = sine_wave(600.0, t) * 0.5
        f2 = sine_wave(1400.0, t) * 0.4
        click = noise_sample() * math.exp(-400.0 * t) * 0.3
        samples.append((f1 + f2 + click) * env * 0.6)
    return samples

def gen_line_clear(duration=0.22):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-12.0 * t)
        # Pitch sweep upward
        freq = 400.0 + (1000.0 * (t / duration)**0.7)
        fm = sine_wave(freq, t) + 0.3 * sine_wave(freq * 2.01, t)
        shimmer = 0.2 * sine_wave(freq * 3.0, t)
        samples.append((fm + shimmer) * env * 0.5)
    return samples

def gen_merge_pop(duration=0.055):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-90.0 * t)
        # Fast pitch glide 400 -> 850 Hz
        freq = 400.0 + (450.0 * math.sqrt(t / duration))
        val = sine_wave(freq, t) * env * 0.6
        samples.append(val)
    return samples

def gen_glass_chime(duration=0.32):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    carrier_freq = 2093.00 # C7
    mod_freq = 3140.0
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-12.0 * t)
        fm_mod = 500.0 * math.exp(-20.0 * t) * sine_wave(mod_freq, t)
        val = sine_wave(carrier_freq + fm_mod, t) * env * 0.5
        # Add harmonic sparkle
        sparkle = 0.2 * sine_wave(carrier_freq * 2.0, t) * math.exp(-18.0 * t)
        samples.append((val + sparkle) * 0.5)
    return samples

def gen_triumphant_chime(duration=0.28):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    # G5 (784Hz) then C6 (1046Hz) arpeggio
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        if t < 0.10:
            freq = 783.99
            t_rel = t
        else:
            freq = 1046.50
            t_rel = t - 0.10
        env = math.exp(-14.0 * t_rel)
        val = (sine_wave(freq, t) + 0.3 * sine_wave(freq * 2.0, t)) * env * 0.5
        samples.append(val)
    return samples

def gen_thud_dull(duration=0.08):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-50.0 * t)
        body = sine_wave(160.0, t) * 0.6
        noise_thud = noise_sample() * math.exp(-120.0 * t) * 0.4
        samples.append((body + noise_thud) * env * 0.6)
    return samples

def gen_key_press(duration=0.03):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-250.0 * t)
        click = noise_sample() * 0.5
        body = sine_wave(350.0, t) * 0.4
        samples.append((click + body) * env * 0.5)
    return samples

def gen_pencil_erase(duration=0.075):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.sin(math.pi * (t / duration))
        val = noise_sample() * env * 0.35
        samples.append(val)
    return samples

def gen_blast_explode(duration=0.38):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-10.0 * t)
        sub = sine_wave(55.0 + 30.0 * math.exp(-15.0 * t), t) * 0.5
        blast = noise_sample() * math.exp(-12.0 * t) * 0.6
        samples.append((sub + blast) * env * 0.6)
    return samples

def gen_laser_shoot(duration=0.095):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-35.0 * t)
        freq = 2400.0 * math.exp(-30.0 * (t / duration))
        val = square_wave(freq, t, duty=0.3) * 0.4 + sine_wave(freq, t) * 0.3
        samples.append(val * env * 0.5)
    return samples

def gen_metal_shing(duration=0.18):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    carrier = 2800.0
    modulator = 4120.0
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-18.0 * t)
        fm = 800.0 * math.exp(-25.0 * t) * sine_wave(modulator, t)
        val = sine_wave(carrier + fm, t) * env * 0.4
        ring = sine_wave(1400.0, t) * math.exp(-15.0 * t) * 0.2
        samples.append((val + ring) * 0.5)
    return samples

def gen_liquid_pour(duration=0.22):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.sin(math.pi * (t / duration))
        # Pitch modulation creating bubble effect
        bubble_freq = 600.0 + 200.0 * sine_wave(25.0, t) + 150.0 * noise_sample()
        val = sine_wave(bubble_freq, t) * env * 0.4
        samples.append(val)
    return samples

def gen_coin_collect(duration=0.16):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        if t < 0.06:
            freq = 1046.50 # C6
            t_rel = t
        else:
            freq = 2093.00 # C7
            t_rel = t - 0.06
        env = math.exp(-25.0 * t_rel)
        val = sine_wave(freq, t) * env * 0.5
        samples.append(val)
    return samples

def gen_chess_land(duration=0.06):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        env = math.exp(-80.0 * t)
        felt = sine_wave(400.0, t) * 0.5
        wood = sine_wave(1100.0, t) * math.exp(-150.0 * t) * 0.3
        samples.append((felt + wood) * env * 0.6)
    return samples

def gen_victory(duration=0.45):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    # C5 (523Hz), E5 (659Hz), G5 (784Hz), C6 (1046Hz) arpeggio sequence
    notes = [523.25, 659.25, 783.99, 1046.50]
    step_duration = 0.09
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        idx = min(int(t / step_duration), len(notes) - 1)
        t_rel = t - (idx * step_duration)
        freq = notes[idx]
        env = math.exp(-10.0 * t_rel)
        if idx == len(notes) - 1:
            env = math.exp(-6.0 * t_rel) # Last note sustains longer
        val = (sine_wave(freq, t) + 0.35 * sine_wave(freq * 2.0, t)) * env * 0.4
        samples.append(val)
    return samples

def gen_defeat(duration=0.40):
    num_samples = int(SAMPLE_RATE * duration)
    samples = []
    # A4 (440Hz), F4 (349Hz), D4 (293Hz) minor triad decay
    notes = [440.0, 349.23, 293.66]
    step_duration = 0.11
    for i in range(num_samples):
        t = i / SAMPLE_RATE
        idx = min(int(t / step_duration), len(notes) - 1)
        t_rel = t - (idx * step_duration)
        freq = notes[idx]
        env = math.exp(-8.0 * t_rel)
        rumble = saw_val = square_wave(freq * 0.5, t) * 0.15
        val = (sine_wave(freq, t) + saw_val) * env * 0.45
        samples.append(val)
    return samples

def main():
    target_dir = os.path.join("app", "src", "main", "res", "raw")
    print(f"Generating 20 consolidated sound effects in {target_dir}...")
    
    sound_catalog = {
        "ui_click.wav": gen_ui_click(),
        "tile_place.wav": gen_tile_place(),
        "block_drop.wav": gen_block_drop(),
        "piece_slide.wav": gen_piece_slide(),
        "snap_connect.wav": gen_snap_connect(),
        "line_clear.wav": gen_line_clear(),
        "merge_pop.wav": gen_merge_pop(),
        "glass_chime.wav": gen_glass_chime(),
        "triumphant_chime.wav": gen_triumphant_chime(),
        "thud_dull.wav": gen_thud_dull(),
        "key_press.wav": gen_key_press(),
        "pencil_erase.wav": gen_pencil_erase(),
        "blast_explode.wav": gen_blast_explode(),
        "laser_shoot.wav": gen_laser_shoot(),
        "metal_shing.wav": gen_metal_shing(),
        "liquid_pour.wav": gen_liquid_pour(),
        "coin_collect.wav": gen_coin_collect(),
        "chess_land.wav": gen_chess_land(),
        "victory.wav": gen_victory(),
        "defeat.wav": gen_defeat(),
        
        # Backward compatibility aliases for existing raw resources if needed
        "click.wav": gen_ui_click(),
        "success.wav": gen_triumphant_chime(),
        "failure.wav": gen_defeat(),
    }
    
    for filename, samples in sound_catalog.items():
        filepath = os.path.join(target_dir, filename)
        create_wav(filepath, samples)

    print("\nAll sounds generated successfully!")

if __name__ == "__main__":
    main()
