# FreeSculpt-AI Architecture

## Goal
Build an original Android app for 3D sculpting and AI-assisted design, while keeping the project legal-safe, extensible and optimized for mobile workflows.

## Layers

### 1. Presentation Layer
- Jetpack Compose screens
- Material 3 design system
- navigation and commands

### 2. Application Layer
- view models
- project state management
- AI assistant commands
- scene commands

### 3. Domain Layer
- sculpt operations
- mesh deformation rules
- animation timeline models
- import/export contracts

### 4. Engine Layer
- renderer interface
- mesh container
- vertex/face updates
- scene graph

### 5. Integration Layer
- Hermes / local model adapters
- file IO
- async data sources

## Conventions
- Omit proprietary code or assets
- Use original implementation
- Keep interfaces open for future features
- Prefer local-only workflows for AI

## Next milestones
1. 3D viewport prototype
2. Sculpt brush pipeline
3. Mesh data abstractions
4. Animation timeline skeleton
5. AI command adapter
6. export/import pipeline
