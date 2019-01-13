package news.motogp.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import news.motogp.application.MotomaniaApplication;
import news.motogp.application.MotomaniaApplication.TrackerName;
import news.motogp.fragment.OrariGaraFragment;
import news.motogp.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class OrariActivity extends FragmentActivity{
	
	FragmentPagerAdapter adapterViewPager;
	
//	int mounth = 2;
//    int day = 26;
//    int hour = 15;
//    int minute = 0;
	private ArrayList<String> categoria;
	private ArrayList<String> tipologia;
	private ArrayList<String> orario;
	
	private ArrayList<Integer> arrayNumberOfDay;
	private ArrayList<Integer> eventiInThisDay;
	
	private String[] monthArray;
	private String[] dayOfWeekArray;
	
	private int firstDay;
	private int mounth;
	
	final String[][] arrayEventi = {


			{"2,17,5,4,6,6",

					"15,0,0,40,Moto2,FP1",
					"15,55,1,40,MotoGP,FP1",
					"16,55,1,40,Moto3,FP2",
					"17,55,1,35,Moto2,FP2",
                    "18,50,1,35,Moto2,FP2",

                    "15,0,0,45,MotoGP,FP2",
					"15,0,0,40,Moto3,FP3",
					"16,55,1,40,Moto2,FP3",
					"17,55,1,40,MotoGP,FP3",

					"15,0,0,40,Moto3,QP",
					"15,55,1,40,Moto2,QP",
					"16,55,1,25,MotoGP,FP4",
					"17,35,0,50,MotoGP,Q1",
					"18,00,0,15,MotoGP,Q2",

					"12,50,1,10,Moto3,WUP",
					"13,25,0,45,Moto2,WUP",
					"14,00,0,20,MotoGP,WUP",
					"15,00,!,!,Moto3,RAC",
					"16,20,!,!,Moto2,RAC",
					"18,00,!,!,MotoGP,RAC"
			},

			//0
			/*{"2,26,5,4,5,6",

			"15,0,0,40,Moto3,FP1",
			"15,55,1,40,Moto2,FP1",
			"16,55,1,40,MotoGP,FP1",
			"17,55,1,35,Moto3,FP2",
			"18,50,1,35,Moto2,FP2",
			
			"15,0,0,45,MotoGP,FP2",
			"15,0,0,40,Moto3,FP3",
			"16,55,1,40,Moto2,FP3",
			"17,55,1,40,MotoGP,FP3",
			
			"15,0,0,40,Moto3,QP",
			"15,55,1,40,Moto2,QP",
			"16,55,1,25,MotoGP,FP4",
			"17,35,0,50,MotoGP,Q1",
			"18,00,0,15,MotoGP,Q2",
			
			"12,50,1,10,Moto3,WUP",
			"13,25,0,45,Moto2,WUP",
			"14,00,0,20,MotoGP,WUP",
			"15,00,!,!,Moto3,RAC",
			"16,20,!,!,Moto2,RAC",
			"18,00,!,!,MotoGP,RAC"
			},*/
			//1
			{"3,10,6,8,6",
				
			"14,0,0,40,Moto3,FP1",
			"14,55,1,40,MotoGP,FP1",
			"15,55,1,40,Moto2,FP1",
			"18,10,0,50,Moto3,FP2",
			"19,5,0,50,MotoGP,FP2",
			"20,5,0,50,Moto2,FP2",
			
			"14,0,0,40,Moto3,FP3",
			"14,55,1,40,MotoGP,FP3",
			"15,55,1,40,Moto2,FP3",
			"17,35,1,15,Moto3,QP",
			"18,30,1,0,MotoGP,FP4",
			"19,10,0,25,MotoGP,Q1",
			"19,35,0,50,MotoGP,Q2",
			"20,5,0,50,Moto2,QP",
			
			"13,40,1,0,Moto3,WUP",
			"14,10,0,30,Moto2,WUP",
			"14,40,1,0,MotoGP,WUP",
			"16,0,!,!,Moto3,RAC",
			"17,20,!,!,Moto2,RAC",
			"19,0,!,!,MotoGP,RAC",
			},
			//2
			{ "3,17,6,8,6",
			"12,0,0,40,Moto3,FP1",
			"12,55,1,40,MotoGP,FP1",
			"13,55,1,40,Moto2,FP1",
			"16,10,0,50,Moto3,FP2",
			"17,5,0,50,MotoGP,FP2",
			"18,5,0,50,Moto2,FP2",
			
			"12,0,0,40,Moto3,FP3",
			"12,55,1,40,MotoGP,FP3",
			"13,55,1,40,Moto2,FP3",
			"15,35,1,15,Moto3,QP",
			"16,30,1,0,MotoGP,FP4",
			"17,10,0,25,MotoGP,Q1",
			"17,35,0,50,MotoGP,Q2",
			"18,5,0,50,Moto2,QP",
			
			"12,40,1,0,Moto3,WUP",
			"13,10,0,30,Moto2,WUP",
			"13,40,1,0,MotoGP,WUP",
			"16,0,!,!,Moto3,RAC",
			"17,20,!,!,Moto2,RAC",
			"19,0,!,!,MotoGP,RAC",
			},
			//3
			{ "4,1,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			//4
			{ "4,15,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			//5
			{ "4,29,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			//6
			{ "5,12,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			//7
			{ "5,25,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			
			//8
			{ "6,10,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			
			//9
			{ "7,7,6,8,6",
			"13,0,0,40,Moto3,FP1",
			"13,55,1,40,MotoGP,FP1",
			"14,55,1,40,Moto2,FP1",
			"17,10,0,50,Moto3,FP2",
			"18,5,0,50,MotoGP,FP2",
			"19,5,0,50,Moto2,FP2",
			
			"13,0,0,40,Moto3,FP3",
			"13,55,1,40,MotoGP,FP3",
			"14,55,1,40,Moto2,FP3",
			"16,35,1,15,Moto3,QP",
			"17,30,1,0,MotoGP,FP4",
			"18,10,0,25,MotoGP,Q1",
			"18,35,0,50,MotoGP,Q2",
			"19,5,0,50,Moto2,QP",
			
			"12,40,1,0,Moto3,WUP",
			"13,10,0,30,Moto2,WUP",
			"13,40,1,0,MotoGP,WUP",
			"15,0,!,!,Moto3,RAC",
			"16,20,!,!,Moto2,RAC",
			"18,0,!,!,MotoGP,RAC",
			},
			
			//10
			{ "7,14,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			//11
			{ "7,28,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"7,0,0,20,Moto2,WUP",
			"7,30,0,50,MotoGP,WUP",
			"8,0,0,20,Moto3,WUP",
			"9,20,!,!,Moto2,RAC",
			"11,0,!,!,MotoGP,RAC",
			"12,30,!,!,Moto3,RAC",
			},
			//12
			{ "8,11,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			//13
			{ "8,25,6,8,6",
			"7,0,0,40,Moto3,FP1",
			"7,55,1,40,MotoGP,FP1",
			"8,55,1,40,Moto2,FP1",
			"11,10,0,50,Moto3,FP2",
			"12,5,0,50,MotoGP,FP2",
			"13,5,0,50,Moto2,FP2",
			
			"7,0,0,40,Moto3,FP3",
			"7,55,1,40,MotoGP,FP3",
			"8,55,1,40,Moto2,FP3",
			"10,35,1,15,Moto3,QP",
			"11,30,1,0,MotoGP,FP4",
			"12,10,0,25,MotoGP,Q1",
			"12,35,0,50,MotoGP,Q2",
			"13,5,0,50,Moto2,QP",
			
			"6,40,1,0,Moto3,WUP",
			"7,10,0,30,Moto2,WUP",
			"7,40,1,0,MotoGP,WUP",
			"9,0,!,!,Moto3,RAC",
			"10,20,!,!,Moto2,RAC",
			"12,0,!,!,MotoGP,RAC",
			},
			//14
			{ "9,9,6,9,5",
			"0,0,0,40,Moto3,FP1",
			"0,55,1,40,MotoGP,FP1",
			"1,55,1,40,Moto2,FP1",
			"4,10,0,50,Moto3,FP2",
			"5,5,0,50,MotoGP,FP2",
			"6,5,0,50,Moto2,FP2",
			
			"0,0,0,40,Moto3,FP3",
			"0,55,1,40,MotoGP,FP3",
			"1,55,1,40,Moto2,FP3",
			"3,35,1,15,Moto3,QP",
			"4,30,1,0,MotoGP,FP4",
			"5,10,0,25,MotoGP,Q1",
			"5,35,0,50,MotoGP,Q2",
			"6,5,0,50,Moto2,QP",
			
			"23,40,1,0,Moto3,WUP",
			"0,10,0,30,Moto2,WUP",
			"0,40,1,0,MotoGP,WUP",
			"2,0,!,!,Moto3,RAC",
			"3,20,!,!,Moto2,RAC",
			"5,0,!,!,MotoGP,RAC",
			},
			//15
			{ "9,15,2,6,7,5",
			"23,0,0,40,Moto3,FP1",
			"23,55,1,40,MotoGP,FP1",
			"0,55,1,40,Moto2,FP1",
			"3,10,0,50,Moto3,FP2",
			"4,5,0,50,MotoGP,FP2",
			"5,5,0,50,Moto2,FP2",
			
			"23,0,0,40,Moto3,FP3",
			"23,55,1,40,MotoGP,FP3",
			"0,55,1,40,Moto2,FP3",
			"2,35,1,15,Moto3,QP",
			"3,30,1,0,MotoGP,FP4",
			"4,10,0,25,MotoGP,Q1",
			"4,35,0,50,MotoGP,Q2",
			"5,5,0,50,Moto2,QP",
			
			"23,40,1,0,Moto3,WUP",
			"0,10,0,30,Moto2,WUP",
			"0,40,1,0,MotoGP,WUP",
			"2,0,!,!,Moto3,RAC",
			"3,20,!,!,Moto2,RAC",
			"5,0,!,!,MotoGP,RAC",
			},
			//16
			{ "9,23,6,8,6",
			"1,0,0,40,Moto3,FP1",
			"1,55,1,40,MotoGP,FP1",
			"2,55,1,40,Moto2,FP1",
			"5,10,0,50,Moto3,FP2",
			"6,5,0,50,MotoGP,FP2",
			"7,5,0,50,Moto2,FP2",
			
			"1,0,0,40,Moto3,FP3",
			"1,55,1,40,MotoGP,FP3",
			"2,55,1,40,Moto2,FP3",
			"4,35,1,15,Moto3,QP",
			"5,30,1,0,MotoGP,FP4",
			"6,10,0,25,MotoGP,Q1",
			"6,35,0,50,MotoGP,Q2",
			"7,5,0,50,Moto2,QP",
			
			"0,40,2,0,Moto3,WUP",
			"2,10,0,30,Moto2,WUP",
			"2,40,1,0,MotoGP,WUP",
			"4,0,!,!,Moto3,RAC",
			"5,20,!,!,Moto2,RAC",
			"7,0,!,!,MotoGP,RAC",
			},
			//17
			{ "10,6,6,8,6",
			"8,0,0,40,Moto3,FP1",
			"8,55,1,40,MotoGP,FP1",
			"9,55,1,40,Moto2,FP1",
			"12,10,0,50,Moto3,FP2",
			"13,5,0,50,MotoGP,FP2",
			"14,5,0,50,Moto2,FP2",
			
			"8,0,0,40,Moto3,FP3",
			"8,55,1,40,MotoGP,FP3",
			"9,55,1,40,Moto2,FP3",
			"11,35,1,15,Moto3,QP",
			"12,30,1,0,MotoGP,FP4",
			"13,10,0,25,MotoGP,Q1",
			"13,35,0,50,MotoGP,Q2",
			"14,5,0,50,Moto2,QP",
			
			"7,40,1,0,Moto3,WUP",
			"8,10,0,30,Moto2,WUP",
			"8,40,1,0,MotoGP,WUP",
			"10,0,!,!,Moto3,RAC",
			"11,20,!,!,Moto2,RAC",
			"13,0,!,!,MotoGP,RAC",
			},
	};

	private int nDay;

	private class MyPagerAdapter extends FragmentPagerAdapter {
	        public MyPagerAdapter(FragmentManager fragmentManager) {
	            super(fragmentManager);
	        }

	        // Returns total number of pages
	        @Override
	        public int getCount() {
	            return arrayNumberOfDay.size();
	        }

	        // Returns the fragment to display for that page
	        @Override
	        public Fragment getItem(int position) {
	        	String[] strCategoria;
            	String[] strTipologia;
            	String[] strOrario;
	            switch (position) {
	            case 0:
	            	Log.d("OrariActivity", "eventiInThisDay.get(0):"+ eventiInThisDay.get(0));
	            	strCategoria = categoria.subList(0, eventiInThisDay.get(0)).toArray(new String[eventiInThisDay.get(0)]);
	            	strTipologia = tipologia.subList(0, eventiInThisDay.get(0)).toArray(new String[eventiInThisDay.get(0)]);
	            	strOrario = orario.subList(0, eventiInThisDay.get(0)).toArray(new String[eventiInThisDay.get(0)]);
	                return OrariGaraFragment.newInstance(0, (firstDay + position) +" "+ monthArray[mounth],strCategoria, strTipologia, strOrario);
	            case 1:
	            	strCategoria = categoria.subList(eventiInThisDay.get(0), eventiInThisDay.get(1)).toArray(new String[eventiInThisDay.get(0)]);
	            	strTipologia = tipologia.subList(eventiInThisDay.get(0), eventiInThisDay.get(1)).toArray(new String[eventiInThisDay.get(0)]);
	            	strOrario = orario.subList(eventiInThisDay.get(0), eventiInThisDay.get(1)).toArray(new String[eventiInThisDay.get(0)]);
	                return OrariGaraFragment.newInstance(1, (firstDay + position) +" "+ monthArray[mounth],strCategoria, strTipologia, strOrario);
	            case 2:
	            	strCategoria = categoria.subList(eventiInThisDay.get(1), eventiInThisDay.get(2)).toArray(new String[eventiInThisDay.get(0)]);
	            	strTipologia = tipologia.subList(eventiInThisDay.get(1), eventiInThisDay.get(2)).toArray(new String[eventiInThisDay.get(0)]);
	            	strOrario = orario.subList(eventiInThisDay.get(1), eventiInThisDay.get(2)).toArray(new String[eventiInThisDay.get(0)]);
	                return OrariGaraFragment.newInstance(2, (firstDay + position) +" "+ monthArray[mounth],strCategoria, strTipologia, strOrario);
	            case 3:
	            	strCategoria = categoria.subList(eventiInThisDay.get(2), eventiInThisDay.get(3)).toArray(new String[eventiInThisDay.get(0)]);
	            	strTipologia = tipologia.subList(eventiInThisDay.get(2), eventiInThisDay.get(3)).toArray(new String[eventiInThisDay.get(0)]);
	            	strOrario = orario.subList(eventiInThisDay.get(2), eventiInThisDay.get(3)).toArray(new String[eventiInThisDay.get(0)]);
	                return OrariGaraFragment.newInstance(3, (firstDay + position) +" "+ monthArray[mounth],strCategoria, strTipologia, strOrario);
	            case 4:
	            	strCategoria = categoria.subList(eventiInThisDay.get(3), eventiInThisDay.get(4)).toArray(new String[eventiInThisDay.get(0)]);
	            	strTipologia = tipologia.subList(eventiInThisDay.get(3), eventiInThisDay.get(4)).toArray(new String[eventiInThisDay.get(0)]);
	            	strOrario = orario.subList(eventiInThisDay.get(3), eventiInThisDay.get(4)).toArray(new String[eventiInThisDay.get(0)]);
	                return OrariGaraFragment.newInstance(4, (firstDay + position) +" "+ monthArray[mounth],strCategoria, strTipologia, strOrario);
	            default:
	                return null;
	            }
	        }

	        // Returns the page title for the top indicator
	        @Override
	        public CharSequence getPageTitle(int position) {
	            return dayOfWeekArray[arrayNumberOfDay.get(position)];
	        }
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_orari);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_header);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(Color.BLACK);
        pagerTabStrip.setTextColor(Color.BLACK);
        
        int nEvento = 0;
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nEvento = extras.getInt("NUMBER_OF_EVENT");
            nDay = extras.getInt("NUMBER_OF_DAY");
            Log.d("ORARI ACTIVITY", "extras nDay: "+nDay);
        }
        
        Tracker t = ((MotomaniaApplication) getApplication()).getTracker(TrackerName.APP_TRACKER);
    	t.setScreenName(getString(R.string.title_section4) +" gara: "+nEvento);
    	t.send(new HitBuilders.AppViewBuilder().build());
        
        categoria = new ArrayList<String>();
        tipologia = new ArrayList<String>();
        orario = new ArrayList<String>();

        monthArray = getResources().getStringArray(R.array.month); 
        dayOfWeekArray = getResources().getStringArray(R.array.day_of_week); 
        
        String[] generalData = arrayEventi[nEvento][0].split(",");
        
        
        int numberOfMounth = Integer.parseInt( generalData[0] );
        int numberOfDay = Integer.parseInt( generalData [1] );
        arrayNumberOfDay = new ArrayList<Integer>();
        eventiInThisDay = new ArrayList<Integer>();
        int[] eventiPerGiorno = {
            	-1,
            	-1,
            	-1,
            	-1
        };
        for( int z = 0, i = 2; i< generalData.length; i++, z++){
        	eventiPerGiorno[z] = Integer.parseInt(generalData[i]);
        	if(z!=0){
        		eventiPerGiorno[z] = eventiPerGiorno[z] + eventiPerGiorno[z-1];
        	}
        	else{
        		eventiPerGiorno[z] = eventiPerGiorno[z] + 1;
        	}
        	Log.d("OrariGaraFragment", "eventiPerGiorno"+z+": "+ eventiPerGiorno[z]);
        }
        for(int i=1;i<arrayEventi[nEvento].length;i++){
        	if(eventiPerGiorno[0] == i || eventiPerGiorno[1] == i || eventiPerGiorno[2] == i || eventiPerGiorno[3] == i){
        		++numberOfDay; 
        	}
        	String[] dataOfDay = arrayEventi[nEvento][i].split(",");
        	Log.d("OrariGaraFragment", "numberOfDay"+i+": "+ numberOfDay);
        	GregorianCalendar calendar = new GregorianCalendar(2016, numberOfMounth, numberOfDay, Integer.parseInt(dataOfDay[0]), Integer.parseInt(dataOfDay[1]));
   	     	calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
   	     	Date data = calendar.getTime();
   	     	if(!arrayNumberOfDay.contains( data.getDay() )){
   	     		if(arrayNumberOfDay.size() != 0){
   	     			eventiInThisDay.add(i-1);
   	     		}
   	     		else{
   	     			firstDay = data.getDate();
   	     			mounth = data.getMonth();
   	     		}
   	     		arrayNumberOfDay.add(data.getDay());
   	     	}
   	     	categoria.add(dataOfDay[4]);
   	     	tipologia.add(dataOfDay[5]);
   	     	String minutes;
   	     	String hours;
   	     	if(data.getHours() > 9)
   	     		hours = ""+data.getHours();
	     	else
	     		hours = "0"+ data.getHours();
   	     	if(data.getMinutes() > 9)
   	     		minutes = ""+data.getMinutes();
   	     	else
   	     		minutes = "0"+ data.getMinutes();
   	     	
   	     	StringBuilder orarioEvento = new StringBuilder();
   	     	orarioEvento.append(hours);
   	     	orarioEvento.append(":");
   	     	orarioEvento.append(minutes);
   	     	if(!dataOfDay[2].equals("!")){
   	     		orarioEvento.append(" - ");
   	     		int ore = Integer.parseInt(dataOfDay[0]) + Integer.parseInt( dataOfDay[2] );
   	     		int min = Integer.parseInt(dataOfDay[3]) ;
	   	     	calendar = new GregorianCalendar(2016, numberOfMounth, numberOfDay, ore, min);
	   	     	calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	   	     	data = calendar.getTime();
	   	     	
	   	     	
	   	     	
	   	     	if(data.getHours() > 9)
	   	     		hours = ""+data.getHours();
		     	else
		     		hours = "0"+ data.getHours();
	   	     	if(data.getMinutes() > 9)
		     		minutes = ""+data.getMinutes();
		     	else
		     		minutes = "0"+data.getMinutes();
	   	     	orarioEvento.append(hours);
	   	     	orarioEvento.append(":");
	   	     	orarioEvento.append(minutes);
   	     	}
   	     	Log.d("OrariGaraFragment", "orario: "+ data.getDate());
   	     	Log.d("OrariGaraFragment", "orario: "+ calendar.getTime());
   	     	
   	     	orario.add(orarioEvento.toString());
   	     	
        }
        eventiInThisDay.add(categoria.size());
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        Log.d("ORARI ACTIVITY", "nDay: "+nDay);
        vpPager.setCurrentItem(nDay); 
        vpPager.getAdapter().notifyDataSetChanged(); 
    }

}
