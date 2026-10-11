# AI Integration Guide

## Local AI support
The project is designed to support local or on-device AI agents such as Hermes or other models that can be served via HTTP or local IPC.

## Recommended setup
- Run a local model with Ollama, llama.cpp or a custom inference server
- Keep model requests asynchronous to avoid freezing the viewport
- Process heavy AI operations in a background executor

## Example flow
```text
User prompt -> App command -> AI service -> mesh suggestion or design feedback -> render preview
```

## Target use cases
- suggest sculpt shape changes
- describe mesh topology issues
- recommend rigging layout
- propose design improvements based on prompt

## Security guidance
- prefer local-only inference
- avoid cloud dependency by default
- do not bundle proprietary models without rights
