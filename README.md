# 🖌️ Signature App

A modern **Drawing App** built with **Jetpack Compose**, **MVI architecture**, and **Hilt**.  
Users can draw, change brush color & size, switch between dark and light mode, and save their drawings to the phone gallery.

---

## 🚀 Features

- 🎨 Draw freely using touch gestures  
- 🌈 Change brush **color** and **size** dynamically  
- 🌓 Switch between **Dark** and **Light** themes  
- 🧹 Clear the canvas anytime  
- 💾 Save your drawing directly to the **Gallery**  
- 🧱 Built with **Clean Architecture** + **MVI pattern**

---

## 🧰 Tech Stack

| Layer | Tools / Libraries |
|-------|-------------------|
| **UI** | Jetpack Compose, Material 3 |
| **Architecture** | MVI (Model–View–Intent), Clean Architecture |
| **DI** | Hilt |
| **State Management** | ViewModel + StateFlow |
| **Drawing** | Canvas, Path, Offset |
| **Storage** | MediaStore API |
| **Language** | Kotlin |

## 🏗️ Project Structure

com.agamy.signature│
│
├── data/ │
│ ├── model/ # Data classes (DrawModelPath) │
│ ├── repository/ # Repository layer │
│
├── domain/ │
│ ├── usecase/ # Business logic (SaveDrawingUseCase, etc.) │
│
├── presentation/
│ ├── draw/ # UI Composables and ViewModel
│ ├── theme/ # Colors, Typography, etc.
│
├── di/ # Hilt dependency injection modules
└── MainActivity.kt


| Light Mode | Dark Mode |
|-------------|------------|
| <img src="screenshots/light.png" width="250"/> | <img src="screenshots/dark.png" width="250"/> |

---


1️⃣ Clone the repository  
```bash
git clone https://github.com/yourusername/DrawApp.git

```

```bash
👤 Mohamed Agamy Ramadan
📍 Giza, Egypt
💼 Junior Android Developer
📧 agamy207@gmail.com

MIT License © 2025 Mohamed Agamy Ramadan


