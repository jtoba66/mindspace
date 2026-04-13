package com.mindspace.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMotion
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.mindspace.app.data.api.NetworkModule
import com.mindspace.app.data.repository.SessionRepository
import com.mindspace.app.ui.components.squishyClick
import com.mindspace.app.ui.screens.*
import com.mindspace.app.ui.theme.MindSpaceTheme
import com.mindspace.app.ui.viewmodel.SessionUiState
import com.mindspace.app.ui.viewmodel.SessionViewModel

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindSpaceApp()
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Reflect : Screen("brain_dump", "Reflect", Icons.Default.SelfImprovement)
    object Organize : Screen("dashboard", "Organize", Icons.Default.AutoAwesomeMotion)
    object Journey : Screen("journey", "Journey", Icons.Default.AutoStories)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object Crisis : Screen("crisis_support", "Help", Icons.Default.MedicalServices)
}

val items = listOf(
    Screen.Reflect,
    Screen.Organize,
    Screen.Journey,
    Screen.Settings
)

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MindSpaceApp() {
    val navController = rememberNavController()
    val repository = SessionRepository(NetworkModule.api)
    val viewModel: SessionViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return SessionViewModel(repository) as T
        }
    })
    var showReflectHelp by remember { mutableStateOf(false) }
    var showSettingsHelp by remember { mutableStateOf(false) }

    MindSpaceTheme(darkTheme = viewModel.isDarkMode) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val currentRoute = currentDestination?.route
        val chromeRoutes = remember { items.map { it.route } + "energy_check" }
        val showChrome = currentRoute in chromeRoutes
        val selectedBottomRoute = when (currentRoute) {
            "energy_check" -> Screen.Reflect.route
            else -> currentRoute
        }

        SharedTransitionLayout {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {
                    if (showChrome) {
                        Surface(
                            color = MaterialTheme.colorScheme.background.copy(alpha = 0.92f),
                            shadowElevation = 6.dp
                        ) {
                            TopAppBar(
                                title = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "MindSpace",
                                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                actions = {
                                    TopBarActions(
                                        currentRoute = currentRoute,
                                        onReflectInfoClick = { showReflectHelp = true },
                                        onSettingsInfoClick = { showSettingsHelp = true }
                                    )
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Transparent
                                )
                            )
                        }
                    }
                },
                bottomBar = {
                    if (showChrome) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                            shadowElevation = 18.dp
                        ) {
                            NavigationBar(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.primary,
                                tonalElevation = 0.dp,
                                modifier = Modifier.height(86.dp)
                            ) {
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        icon = { Icon(screen.icon, contentDescription = null) },
                                        label = { Text(screen.title) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true ||
                                            selectedBottomRoute == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary,
                                            indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.28f),
                                            unselectedIconColor = Color(0xFFA1A1AA),
                                            unselectedTextColor = Color(0xFFA1A1AA)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Reflect.route,
                    modifier = Modifier.padding(innerPadding),
                    enterTransition = { 
                        slideInHorizontally(
                            animationSpec = spring(stiffness = Spring.StiffnessLow),
                            initialOffsetX = { it }
                        ) + fadeIn(animationSpec = tween(300))
                    },
                    exitTransition = { 
                        slideOutHorizontally(
                            animationSpec = spring(stiffness = Spring.StiffnessLow),
                            targetOffsetX = { -it }
                        ) + fadeOut(animationSpec = tween(300))
                    },
                    popEnterTransition = { 
                        slideInHorizontally(
                            animationSpec = spring(stiffness = Spring.StiffnessLow),
                            initialOffsetX = { -it }
                        ) + fadeIn(animationSpec = tween(300))
                    },
                    popExitTransition = { 
                        slideOutHorizontally(
                            animationSpec = spring(stiffness = Spring.StiffnessLow),
                            targetOffsetX = { it }
                        ) + fadeOut(animationSpec = tween(300))
                    }
                ) {
                    composable(Screen.Reflect.route) {
                        BrainDumpScreen(
                            animatedVisibilityScope = this,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onNext = { input ->
                                viewModel.currentInput = input
                                navController.navigate("energy_check")
                            },
                            onCrisis = {
                                navController.navigate(Screen.Crisis.route)
                            }
                        )
                    }
                    composable("energy_check") {
                        EnergyCheckScreen(
                            animatedVisibilityScope = this,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onSelectionChanged = { energy -> viewModel.selectedEnergy = energy },
                            onReady = {
                                viewModel.submitBrainDump()
                                navController.navigate("processing")
                            }
                        )
                    }
                    composable("processing") {
                        ProcessingScreen(
                            animatedVisibilityScope = this,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onComplete = {}
                        )
                        LaunchedEffect(viewModel.uiState) {
                            if (viewModel.uiState is SessionUiState.Success) {
                                navController.navigate("dashboard") {
                                    popUpTo(Screen.Reflect.route) { inclusive = false }
                                }
                            } else if (viewModel.uiState is SessionUiState.Error) {
                                navController.popBackStack(Screen.Reflect.route, inclusive = false)
                            }
                        }
                    }
                    composable("dashboard") {
                        DashboardScreen(
                            animatedVisibilityScope = this,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            uiState = viewModel.uiState,
                            onStartReflection = {
                                navController.navigate(Screen.Reflect.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable(Screen.Journey.route) {
                        JourneyScreen(
                            viewModel = viewModel,
                            onStartReflection = {
                                navController.navigate(Screen.Reflect.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(viewModel)
                    }
                    composable(Screen.Crisis.route) {
                        CrisisSupportScreen(onClose = { navController.popBackStack() })
                    }
                }
            }
        }

        if (showReflectHelp) {
            HelpDialog(
                onDismissRequest = { showReflectHelp = false },
                title = "Reflect",
                intro = "A few honest words is enough.",
                items = listOf(
                    "Type or use the mic. MindSpace will sort it gently.",
                    "Private Mode keeps this out of Journey.",
                    "Overwhelmed? takes you to the calmer support screen."
                )
            )
        }

        if (showSettingsHelp) {
            HelpDialog(
                onDismissRequest = { showSettingsHelp = false },
                title = "Settings",
                intro = "Privacy, reminders, and appearance live here.",
                items = listOf(
                    "Private Mode keeps future reflections unsaved.",
                    "Gentle Check Ins controls reminder-style nudges.",
                    "Appearance lets you switch between light and dark."
                )
            )
        }
    }
}

@Composable
private fun TopBarActions(
    currentRoute: String?,
    onReflectInfoClick: () -> Unit,
    onSettingsInfoClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (currentRoute) {
            Screen.Reflect.route -> TopBarBadge(
                icon = Icons.Default.Info,
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                size = 34.dp,
                onClick = onReflectInfoClick
            )
            "energy_check" -> {
                TopBarBadge(
                    icon = Icons.Default.Notifications,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    size = 34.dp
                )
                ProfileBadge()
            }
            Screen.Journey.route -> {
                TopBarBadge(
                    icon = Icons.Default.Search,
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    size = 34.dp
                )
                ProfileBadge()
            }
            Screen.Settings.route -> TopBarBadge(
                icon = Icons.Default.Info,
                backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                size = 34.dp,
                onClick = onSettingsInfoClick
            )
            else -> ProfileBadge()
        }
    }
}

@Composable
private fun TopBarBadge(
    icon: ImageVector,
    backgroundColor: Color,
    tint: Color,
    size: Dp,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(backgroundColor, CircleShape)
            .then(
                if (onClick != null) Modifier.squishyClick(onClick = onClick) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun ProfileBadge() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.28f), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text("J", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
private fun HelpDialog(
    onDismissRequest: () -> Unit,
    title: String,
    intro: String,
    items: List<String>
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
            shadowElevation = 24.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                        )
                    )
                    .padding(28.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Info, 
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = intro,
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items.forEach { item ->
                            Row(verticalAlignment = Alignment.Top) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 6.dp)
                                        .size(6.dp)
                                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                        .squishyClick(onClick = onDismissRequest),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Got it", 
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), 
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
