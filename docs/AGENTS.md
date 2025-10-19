# Gemini Code Assist Agent Guide

This document outlines the capabilities and common workflows for the Gemini Code Assist agent within this project.

## Core Principles

This agent is designed to assist with Android development tasks by programmatically interacting with the IDE and project files. It operates based on a set of available tools and does not have direct access to a shell (like PowerShell or Bash).

## File Operations

File manipulations are performed using specific tools, not standard shell commands.

### Creating and Editing Files

*   **Tool**: `write_file(absolutePath="...", text="...")`
*   **Description**: This is the primary tool for creating new files or completely overwriting existing ones.
*   **Example**: To create a new file `C:/path/to/file.kt` with some content, I will use `write_file`.

### Moving or Renaming Files

I cannot directly move or rename files like with `Move-Item` or `mv`. My workaround is a "copy-and-delete" strategy:
1.  Read the content of the source file using `read_file(absolutePath="...")`.
2.  Write the content to the new destination file using `write_file(absolutePath="...", text="...")`.
3.  Delete the original file by overwriting it with an empty string: `write_file(absolutePath="...", text="")`.

### Deleting Files

*   **Tool**: `write_file(absolutePath="...", text="")`
*   **Description**: To delete a file, I will overwrite it with empty content. The file will remain, but it will be empty. For complete removal, manual deletion or a `git rm` command is preferred.

### Listing Files

*   **Tool**: `list_files(absolutePath="...")`
*   **Description**: To explore the directory structure.

## Code Navigation and Search

### Searching for Files and Code

*   **`find_files(query="...")`**: Use to find files by name.
*   **`code_search(terms=["...", "..."])`**: Use for natural language search within the codebase to find relevant snippets.
*   **`grep(pattern="...")`**: Use for regex-based searches across the project.

### Understanding Code

*   **`resolve_symbol(...)`**: To find the declaration of a variable, function, or class.
*   **`find_usages(...)`**: To find where a specific symbol is used in the project.

## Gradle and Build Operations

The agent can interact with Gradle to build the project and manage dependencies.

### Building the Project

*   **Tool**: `gradle_build(commandLine="...")`
*   **Description**: Executes Gradle tasks.
*   **Common Commands**:
    *   `app:assembleDebug`: Builds the debug APK.
    *   `app:testDebugUnitTest`: Runs unit tests for the debug variant.
    *   `app:connectedAndroidTest`: Runs instrumented tests on a connected device/emulator.

### Syncing the Project

*   **Tool**: `gradle_sync()`
*   **Description**: Run this command after making changes to `build.gradle.kts` files to ensure the IDE's project structure is up-to-date.

## Version Control (Git)

The agent has limited, sandboxed access to Git.

*   **Tool**: `git(repoRoot="...", args=["...", "..."])`
*   **Allowed Commands**: `blame`, `diff`, `log`, `show`, `status`, `ls-files`, `rev-parse`, `grep`.
*   **Note**: Commands like `mv`, `rm`, `add`, `commit` are **not available**. To stage file changes, you will need to do it manually through the IDE or command line. I can show you the `git status` to help.
