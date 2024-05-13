package com.example.studentapp.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.ui.theme.dashboard.DashboardScreen
import com.example.studentapp.ui.theme.home.HomeScreen
import com.example.studentapp.ui.theme.login.LoginScreen
import com.example.studentapp.ui.theme.signup.SignupScreen
import com.example.studentapp.ui.theme.splash.SplashScreen
import com.example.studentapp.ui.theme.students.AddStudentsScreen
import com.example.studentapp.ui.theme.students.ViewStudentsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController:NavHostController = rememberNavController(),
    startDestination:String = SPLASH_URL
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier){
        composable(LOGIN_URL){
            LoginScreen(navController = navController)
        }
        composable(SIGNUP_URL){
            SignupScreen(navController = navController)
        }
        composable(HOME_URL){
            HomeScreen(navController = navController)
        }
        composable(ADD_STUDENTS_URL){
            AddStudentsScreen(navController = navController)
        }
        composable(VIEW_STUDENTS_URL){
            ViewStudentsScreen(navController = navController)
        }
        composable(DASHBOARD_URL){
            DashboardScreen(navController = navController)
        }
        composable(SPLASH_URL){
            SplashScreen(navController = navController)
        }
    }
}