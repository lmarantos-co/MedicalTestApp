package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.cvdriskestimator.R

class leaderBoardActivity : ComponentActivity() {

    private var participantNames = ArrayList<String>()
    private var participantAvatars = ArrayList<Drawable>()
    private var screenHeight : Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold {
//                ConstraintCompose()
                participantList()
            }

        }


    }

//    @Preview
//    @Composable
//    fun ConstraintCompose() {
//
//        val constraints = ConstraintSet {
//            val cvdLayout = createRefFor("cvdLayout")
//            constrain(cvdLayout)
//            {
//                top.linkTo(parent.top)
//                height = Dimension.value(convertPixelsToDp(getScreenDimens(), getParent().applicationContext).dp)
//                start.linkTo(parent.start)
//            }
//        }
//
//        ConstraintLayout( constraints , modifier = Modifier
//            .fillMaxHeight()
//            .fillMaxWidth()) {
//            Box(modifier = Modifier.layoutId("cvdLayout"))
//            {
//
//                AndroidView(
//                  factory =  { context: Context ->
//                        View.inflate(context, R.layout.cvd_title_form , null)
//                    }
//                )
//
//            }
//        }
//    }


    @Preview
    @Composable
    fun participantList()
    {
        Column(modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .border(0.dp , Color.Black , shape = RoundedCornerShape(60.dp)))
        {
            TextTabs()
            val scrollState = rememberLazyListState()
            LazyColumn(state = scrollState, modifier = Modifier.border( 5.dp, Color.DarkGray , shape = RoundedCornerShape(60.dp))){


                items(participantList)
                {
                        participant ->
                    run {
                        participantCard(
                            participantList.indexOf(participant) ,
                            userName = participant.participantName,
                            participantImage = participant.participantDrawable
                        )
                    }
                }
            }
        }

    }

    @Composable
    fun Tab(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        text: @Composable (() -> Unit)? = null,
        icon: @Composable (() -> Unit)? = null,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        selectedContentColor: Color = LocalContentColor.current,
        unselectedContentColor: Color = selectedContentColor.copy(alpha = ContentAlpha.medium)
    )
    {

    }

    @Preview
    @Composable
    fun TextTabs() {
        var tabIndex by remember { mutableStateOf(0) }
        val tabData = listOf(
            "GLOBAL",
            "MY COUNTRY"
        )
        TabRow(selectedTabIndex = tabIndex) {


            tabData.forEachIndexed { index, text ->
                val selected = tabIndex == index
                Tab(selected = tabIndex == index, onClick = {
                    tabIndex = index

                }) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(50.dp)
                            .fillMaxWidth()
                            ,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier
                                .size(24.dp)
                                .align(Alignment.CenterHorizontally)
                                .background(color = if (selected) Color.Red else Color.White)
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun participantCard(
        userID : Int ,
        userName : String , participantImage : Int)
    {
        Card(elevation = 5.dp ,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text( modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 10.dp), color = Color.Black , fontSize = 20.sp , fontFamily = FontFamily.Default ,  textAlign = TextAlign.Center ,
                    text = userID.toString() + ". " + userName )

                Image(painterResource(id = participantImage),  contentDescription = "participant image" , modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clip(
                        CircleShape
                    )   )
                
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp, 0.dp, 0.dp, 5.dp))
                {
                    Text(color = Color.Black, fontSize = 20.sp, modifier =
                    Modifier
                        .border(2.dp, Color.Gray)
                        .padding(10.dp) , text = "Score : " + setParticipantScore(userID ).toString())
                }
                Image(painterResource(id = R.drawable.ic_trophy) , contentDescription = "Participant Trophy"
                    , modifier = Modifier.size(20.dp , 20.dp))

            }
        }
    }
    
    private fun setParticipantScore(id : Int) : Int
    {
        return ((10000) - (id * 500))
    }

    private fun getScreenDimens() : Float
    {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        return height * 0.14f
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    val participantList = listOf<participant>(
        participant("Lampros " , R.drawable.avatar) ,
        participant("Chris" , R.drawable.avatar_b) ,
        participant("Dimitris" , R.drawable.beard) ,
        participant("Kostas" , R.drawable.boy) ,
        participant("Panagiotis" , R.drawable.gamer) ,
        participant("Nikoleta" , R.drawable.woman) ,
        participant("Dimitra" , R.drawable.womanb) ,
        participant("Kyriakos" , R.drawable.man_a) ,
        participant("Giannis" , R.drawable.man_b)
    )

    data class participant(
        var participantName : String ,
        var participantDrawable : Int
    )



}

