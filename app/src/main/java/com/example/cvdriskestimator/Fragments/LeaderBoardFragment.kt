package com.example.cvdriskestimator.Fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.cvdriskestimator.customClasses.PopUpMenu
import com.example.cvdriskestimator.customClasses.leaderBoardRecyclerAdapter
import com.example.cvdriskestimator.MainActivity
import com.example.cvdriskestimator.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [LeaderBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderBoardFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var medTestForm : ConstraintLayout
    private lateinit var termsOfUse : ConstraintLayout
    private lateinit var loginFragment: LoginFragment
    private lateinit var registerFragment: RegisterFragment
    private lateinit var mainActivity: MainActivity
    private lateinit var popUpMenu: PopUpMenu

    private lateinit var leaderboardRecyclerView: RecyclerView
    private lateinit var leaderBoardRecyclerAdapter: leaderBoardRecyclerAdapter
    private var participantNames = ArrayList<String>()
    private var participantAvatars = ArrayList<Drawable>()


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
        return inflater.inflate(R.layout.leaderboard_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medTestForm = view.findViewById(R.id.medicalEstimatorForm)
        termsOfUse = view.findViewById(R.id.termsOfUseRelLayout)
        medTestForm.findViewById<View>(R.id.userIcon).alpha = 1f
        loginFragment = LoginFragment.newInstance()
        registerFragment = RegisterFragment.newInstance()
        popUpMenu = PopUpMenu(termsOfUse.findViewById(R.id.termsRelLayout) , mainActivity , this , loginFragment , registerFragment , null ,this  )

        medTestForm.findViewById<View>(R.id.userIcon).setOnClickListener {
            popUpMenu.showPopUp(view)
        }

        termsOfUse.findViewById<View>(R.id.termsOfUseRelLayout).setOnClickListener {
            termsOfUse.findViewById<View>(R.id.termsOfUseRelLayout).visibility = View.INVISIBLE
        }

        medTestForm.setOnClickListener {
            mainActivity.backToActivity()
        }

        initLeaderRecyclerView(view)

    }

    private fun initLeaderRecyclerView(view : View)
    {
        leaderboardRecyclerView = view.findViewById(R.id.leaderBoardRecyclerView)
        populateDataForRecyclerView()
        leaderBoardRecyclerAdapter = leaderBoardRecyclerAdapter()
        leaderBoardRecyclerAdapter.setActivity(mainActivity)
        leaderBoardRecyclerAdapter.nameDataSet = participantNames
        leaderBoardRecyclerAdapter.participantAvatars = participantAvatars
        leaderboardRecyclerView.apply {
            adapter = leaderBoardRecyclerAdapter
        }
//        var GridLayoutManager = GridLayoutManager(mainActivity.applicationContext , 1 , GridLayoutManager.VERTICAL, false)
//        var spannedGridLayoutManager = SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 4)
//        spannedGridLayoutManager = spannedGridLayoutManager.spanSizeLookup = { position ->
//             SpanSize(2, 2);
//        }
    }

    private fun populateDataForRecyclerView()
    {
        participantNames.add("Lamrpos")
        participantNames.add("Chris")
        participantNames.add("Dimitris")
        participantNames.add("Kostas")
        participantNames.add("Panagiotis")
        participantNames.add("Nikoleta")
        participantNames.add("Dimitra")
        participantNames.add("Kyriakos")
        participantNames.add("Giannis")

        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.avatar))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.avatar_b))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.beard))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.boy))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.gamer))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.woman))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.womanb))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.man_a))
        participantAvatars.add(mainActivity.resources.getDrawable(R.drawable.man_b))

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    companion object {

        // TODO: Rename and change types and number of parameters

        @JvmStatic
        fun newInstance() =
            LeaderBoardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}