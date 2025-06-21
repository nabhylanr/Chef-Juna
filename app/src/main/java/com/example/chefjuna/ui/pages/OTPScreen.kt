package com.example.chefjuna.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefjuna.ui.navigation.NavigationRoutes.HOME
import com.example.chefjuna.ui.navigation.NavigationRoutes.OTP

import androidx.navigation.NavController

@Composable
fun OTPScreen(
    navController: NavController,
    onOTPSubmit: (String) -> Unit = {}
) {
    val otpLength = 4
    var otpValues by remember { mutableStateOf(List(otpLength) { "" }) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification Code",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF508130),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We have sent the verification code to your email address",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                otpValues.forEachIndexed { index, value ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otpValues = otpValues.toMutableList().also { list -> list[index] = it }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(60.dp)
                            .height(64.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF508130),
                            unfocusedBorderColor = Color(0xFF508130),
                            cursorColor = Color(0xFF508130)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Resend code?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val fullOTP = otpValues.joinToString("")
                    onOTPSubmit(fullOTP)
                    navController.navigate(HOME) {
                        popUpTo(OTP) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF508130)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Verify", color = Color.White)
            }
        }
    }
}