# Personal AI Assistant

A personal AI assistant built with Java, Ollama, and Llama 3.2 1B, designed to run locally on a PC.

## Features

- **Local AI Chat:** Chat with a locally hosted language model through Ollama.
- **Personal Memory:** Save, update, retrieve, and delete personal facts.
- **Task Management:** Create, view, complete, and delete tasks.
- **Task Priorities:** Organize tasks by High, Medium, and Low priority.
- **Deadlines and Reminders:** Set deadlines and receive reminders for pending tasks.
- **Persistent Storage:** Store chat history, memory, and tasks locally in JSON files.
- **Calculator:** Perform calculations through the assistant.
- **Date and Time:** Check the current date and time.
- **Help Menu:** View supported commands.

## Technology Stack

- Java
- Ollama
- Llama 3.2 1B
- Gson
- Java HTTP Client
- JSON file storage

## Requirements

- Java with support for the Java HTTP Client API
- [Ollama](https://ollama.com/)
- The `llama3.2:1b` model
- Gson 2.13.2

## Getting Started

1. Install Java and Ollama.
2. Download the required model:

   ```bash
   ollama pull llama3.2:1b
   ```

3. Ensure Ollama is running locally.
4. Open the project in your Java IDE.
5. Add `lib/gson-2.13.2.jar` to the project's classpath.
6. Run `Main.java`.

Configure the Gson classpath according to your IDE or Java build setup.

## Project Status

The Java-based assistant prototype is operational. ESP32-S3 hardware integration and voice interaction are planned future enhancements and are not yet implemented.

## Future Plans

- Integrate an ESP32-S3 device over Wi-Fi.
- Add voice input and audio responses.
- Develop a dedicated user interface.
- Expand assistant capabilities.

## Privacy

Personal memory, chat history, and task data are intended to remain in local JSON files and should not be committed to the public repository.

## License

No license has been selected yet.
