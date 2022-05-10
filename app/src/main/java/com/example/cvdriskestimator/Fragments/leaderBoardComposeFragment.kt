package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [leaderBoardComposeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class leaderBoardComposeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var prMainActivity : MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.leaderBoardComposeView).setContent {
            val scaffoldState = rememberScaffoldState()
            Scaffold(scaffoldState = scaffoldState) {
                ConstraintCompose()
                participantList()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prMainActivity = context as MainActivity
    }

    @Preview
    @Composable
    fun ConstraintCompose() {

        val constraints = ConstraintSet {
            val cvdLayout = createRefFor("cvdLayout")
            constrain(cvdLayout)
            {
                top.linkTo(parent.top)
//                height = Dimension.value(convertPixelsToDp(getScreenDimens(), prMainActivity.applicationContext).dp)
                start.linkTo(parent.start)
            }
        }

        ConstraintLayout( constraints , modifier = Modifier
            .fillMaxHeight(convertPixelsToDp(getScreenDimens(), prMainActivity.applicationContext))
            .fillMaxWidth()) {
            Box(modifier = Modifier.layoutId("cvdLayout"))
            {

                AndroidView(
                    factory =  { context: Context ->
                        View.inflate(context, R.layout.cvd_title_form , null)
                    }
                )

            }
        }
    }


    @Preview
    @Composable
    fun participantList()
    {
        Column(modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(60.dp))
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
            )
        {
            TextTabs()
            val scrollState = rememberLazyListState()
            LazyColumn(state = scrollState) {


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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text( modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 10.dp), color = Color.Black , fontSize = 20.sp , fontFamily = FontFamily.Default ,  textAlign = TextAlign.Center ,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Green,
                                fontSize = 25.sp
                            )
                        )
                        {
                            append(userID.toString().get(0))
                        }
                        append(". $userName")
                    })

                Image(
                    painterResource(id = participantImage),  contentDescription = "participant image" , modifier = Modifier
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
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painterResource(id = R.drawable.ic_trophy) , contentDescription = "Participant Trophy"
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
        prMainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
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


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            leaderBoardComposeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}