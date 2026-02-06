# AI Reading Helper

A clean Android starter with Jetpack Compose, responsive layout, and all dependencies configured.

## 🏗️ Project Structure

```
app/src/main/java/com/cabbage/aireader/
├── AIReaderApplication.kt        # Hilt Application
├── MainActivity.kt               # Entry point
│
├── MainScreen.kt                 # Responsive layout coordinator
├── CompositeViewModel.kt         # Layout state management
│
├── ReadingScreen.kt              # Reading view with text area + FAB
├── ReadingViewModel.kt           # Reading state
│
├── AssistantScreen.kt            # AI assistant chat view
├── AssistantViewModel.kt         # Assistant state
│
├── Color.kt                      # Material3 Colors
├── Theme.kt                      # Material3 Theme
└── Type.kt                       # Typography
```

## 🎨 UI Features

### 📱 Mobile Layout (< 600dp width or Portrait)
- **Reading View**: Full screen with text area
- **FAB**: Floating button to open AI Assistant
- **Assistant**: Modal bottom sheet (80% height)

### 💻 Tablet Layout (≥ 600dp width + Landscape)
- **Side-by-Side**: Reading view on left, Assistant on right
- **Split Screen**: Both views visible simultaneously
- **Responsive**: Automatically switches based on screen size

## 📦 Dependencies (All Ready to Use)

### Core
- Kotlin 2.0.20
- Jetpack Compose (Material3, Navigation)
- Coroutines & Flow

### AI & Network
- ✅ Google AI SDK (Gemini) - Ready for AI integration
- ✅ Retrofit + OkHttp - API calls
- ✅ Gson - JSON parsing

### Database
- ✅ Room Database - Local storage
- ✅ KSP - Annotation processing

### Architecture
- ✅ ViewModel with StateFlow
- ✅ Hilt Dependency Injection
- ✅ Lifecycle components

### Code Quality
- ✅ ktfmt with kotlinLangStyle

## 🚀 Getting Started

### 1. Open Project
Open in Android Studio and sync Gradle.

### 2. Run
Click Run button - the app will display:
- **Tablet/Landscape**: Two-panel layout
- **Mobile/Portrait**: Reading view with FAB

### 3. Format Code
```bash
./gradlew ktfmtFormat
```

## 🎯 Features Implemented

### ✅ Reading Screen
- Large text area for reading content
- Scrollable text field
- FAB to open AI Assistant (mobile only)

### ✅ AI Assistant Screen
- Chat interface layout
- Message input with send button
- Ready for AI integration

### ✅ Responsive Layout
- Automatic layout detection
- Side-by-side for tablets
- Bottom sheet for mobile
- Smooth transitions

### ✅ State Management
- Separate ViewModels for each screen
- StateFlow for reactive updates
- Layout mode management

## 📝 Quick Start Examples

### Adding Gemini AI

In `AssistantViewModel.kt`:

```kotlin
@HiltViewModel
class AssistantViewModel @Inject constructor() : ViewModel() {
    
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "YOUR_API_KEY"
    )
    
    fun sendMessage() {
        viewModelScope.launch {
            val response = generativeModel.generateContent(message)
            // Handle response
        }
    }
}
```

### Saving Chat History with Room

Create entities:

```kotlin
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Long
)
```

### Using Retrofit for API

```kotlin
interface ApiService {
    @GET("analyze")
    suspend fun analyzeText(@Body text: String): Response<AnalysisResult>
}
```

## 🎨 Customization

### Modify Reading Screen

Edit `ReadingScreen.kt` to add features like:
- Text formatting options
- Font size controls
- Highlighting
- Bookmarks

### Enhance Assistant Screen

Edit `AssistantScreen.kt` to add:
- Message history display
- User/AI message bubbles
- Loading indicators
- Error handling

### Adjust Layout Breakpoint

In `MainScreen.kt`, change the tablet detection:

```kotlin
when {
    screenWidthDp >= 840 && isLandscape -> LayoutMode.SIDE_BY_SIDE  // Larger tablets only
    else -> LayoutMode.BOTTOM_SHEET
}
```

## 🛠️ Available Gradle Tasks

```bash
# Build and run
./gradlew installDebug

# Format code
./gradlew ktfmtFormat

# Check style
./gradlew ktfmtCheck

# Clean build
./gradlew clean build
```

## 📚 Tech Stack

- **Language**: Kotlin 2.0.20
- **UI**: Jetpack Compose + Material3
- **Architecture**: MVVM (ViewModel + StateFlow)
- **DI**: Hilt
- **AI**: Google Generative AI SDK (Gemini)
- **Database**: Room
- **Network**: Retrofit + OkHttp + Gson
- **Async**: Coroutines & Flow
- **Code Style**: ktfmt (kotlinLangStyle)

## 🎓 Architecture Philosophy

**Simple structure, powerful tools**

- ✅ **Clean separation**: Reading and Assistant as distinct features
- ✅ **Responsive design**: One codebase, multiple layouts
- ✅ **Easy to extend**: Add AI, database, or network features as needed
- ✅ **Production-ready**: All dependencies configured

## 🔧 Next Steps

1. **Add API Key**: Configure Gemini API key
2. **Implement AI**: Connect `AssistantViewModel` to Gemini SDK
3. **Save Data**: Use Room to persist chat history
4. **Enhance UI**: Add message bubbles, loading states, errors

## 📄 License

[Add your license here]
