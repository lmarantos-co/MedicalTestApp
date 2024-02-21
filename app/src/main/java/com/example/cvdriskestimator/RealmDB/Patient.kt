package com.example.cvdriskestimator.RealmDB

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RealmClass
open class Patient() : RealmModel {

    @PrimaryKey
    var patientId : String = ""

    //User Data

    @Required
    var userName : String = ""

    @Required
    var password : String = ""

    @Required
    var patientName : String = ""

    @Required
    var patientLastName : String = ""

    @Required
    var dateOfBirth : String = ""

    @Required
    var occupation : String = ""

    var yearsOfApprentice : Int = 0

    var listOfBAITests : RealmList<BAITest>? = null
    var listOfBDITests : RealmList<BDITest>? = null
    var listOfBPITests : RealmList<BPITest>? = null
    var listOfCVDTests : RealmList<CVDTest>? = null
    var listOfDASSTests : RealmList<DASSTest>? = null
    var listOfDiabetesTests : RealmList<DiabetesTest>? = null
    var listOfGDSTests : RealmList<GDSTest>? = null
    var listOfHAMTests : RealmList<HAMTest>? = null
    var listOfMDITests : RealmList<MDITest>? = null
    var listOfMDSTests : RealmList<MDSTest>? = null
    var listOfSTAITests : RealmList<STAITest>? = null
    var listOfZUNGTests : RealmList<ZUNGTest>? = null


}