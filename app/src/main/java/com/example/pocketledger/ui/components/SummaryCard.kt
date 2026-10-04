package com.example.pocketledger.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pocketledger.ui.theme.AppTextBlack

@Composable
fun SummaryCard(
    title: String,
    name: String,
    amont: String,
    icon: ImageVector,
    containerColors: Color,
    modifier: Modifier = Modifier
){
   Card(
       modifier = modifier,
       colors= CardDefaults.cardColors(

           containerColor = containerColors
       ),
       shape = RoundedCornerShape(12.dp)
   ){
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(12.dp)
       ) {
               Text(
                   text = title,
                   style = MaterialTheme.typography.titleLarge,
                   color = AppTextBlack,
                   fontWeight = FontWeight.Bold
                   )

           Spacer(
               modifier = Modifier.height(8.dp)
           )
           Text(
               text = name,
               style = MaterialTheme.typography.titleSmall,
               color = AppTextBlack,
           )
           Spacer(
               modifier = Modifier.height(8.dp)
           )
           Row(
               horizontalArrangement = Arrangement.End
           ){
//               Spacer(
//                   modifier = Modifier.height(24.dp)
//               )
               Text(
                   text = amont,
                   style = MaterialTheme.typography.titleMedium,
                   fontWeight = FontWeight.Bold,
                   color = AppTextBlack,
               )
           }

       }
   }
   }
