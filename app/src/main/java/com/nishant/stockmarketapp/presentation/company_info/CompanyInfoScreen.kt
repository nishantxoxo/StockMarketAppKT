package com.nishant.stockmarketapp.presentation.company_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nishant.stockmarketapp.ui.theme.DarkBlue
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun CompanyInfoScreen(
    symbol : String,
    viewModel: CompanyInfoViewModel = hiltViewModel()
){
    val state = viewModel.state
    if(state.error == null){
        Column(modifier = Modifier.fillMaxSize()
            .background(DarkBlue).padding(16.dp)
        ) {
            state.company?.let { comp ->
                Text(
                    text = comp.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = comp.symbol,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
//                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "INDUSTRY : ${comp.industry}",
//                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Country : ${comp.country}",
//                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Divider(
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = comp.description,
//                    fontStyle = FontStyle.Italic,
                    fontSize = 12.sp,
//                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                if(state.stockInfos.isNotEmpty()){
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Market Summary")
                    Spacer(modifier = Modifier.height(12.dp))


                    StockChart(infos = state.stockInfos, modifier = Modifier.fillMaxWidth().height(250.dp).align(Alignment.CenterHorizontally))
                }

            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
        ){
        if(state.isLoading){
            CircularProgressIndicator()
        }
        else if(state.error != null){
            Text(text = state.error, color=MaterialTheme.colorScheme.error )
        }
    }
}