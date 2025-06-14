package com.example.chefjuna.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefjuna.R
import androidx.compose.ui.Alignment
import com.example.chefjuna.ui.navigation.NavigationRoutes.PROFILE
import com.example.chefjuna.ui.navigation.NavigationRoutes.LOGIN
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    username: String = "Arnold Poernomo",
    email: String = "arnold@chefjuna.com",
    navController: NavController,
) {
    var isEditing by remember { mutableStateOf(false) }
    var editUsername by remember { mutableStateOf(username) }
    var editEmail by remember { mutableStateOf(email) }
    var currentUsername by remember { mutableStateOf(username) }
    var currentEmail by remember { mutableStateOf(email) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF508130),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Account Details", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    if (isEditing) {
                        Row {
                            IconButton(
                                onClick = {
                                    // Save changes
                                    currentUsername = editUsername
                                    currentEmail = editEmail
                                    isEditing = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Save",
                                    tint = Color(0xFF508130)
                                )
                            }
                            IconButton(
                                onClick = {
                                    // Cancel changes
                                    editUsername = currentUsername
                                    editEmail = currentEmail
                                    isEditing = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cancel",
                                    tint = Color.Gray
                                )
                            }
                        }
                    } else {
                        IconButton(
                            onClick = {
                                editUsername = currentUsername
                                editEmail = currentEmail
                                isEditing = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFF508130)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.selfie),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Username", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                if (isEditing) {
                    OutlinedTextField(
                        value = editUsername,
                        onValueChange = { editUsername = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF508130),
                            cursorColor = Color(0xFF508130)
                        ),
                        singleLine = true
                    )
                } else {
                    Text(currentUsername, fontSize = 16.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Email", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                if (isEditing) {
                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF508130),
                            cursorColor = Color(0xFF508130)
                        ),
                        singleLine = true
                    )
                } else {
                    Text(currentEmail, fontSize = 16.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate(LOGIN) {
                    popUpTo(PROFILE) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF508130)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Logout", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}