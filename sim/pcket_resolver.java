import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class PacketResolver {
	// Per Sample Flags for ECG --> definition of EcgInFlags-------------->>>>
	final static int LEAD_OPEN_ECG = 0x01;
	final static int PM_PULSE = 0x02;
	final static int NEW_BEAT_ECG = 0x04;
	final static int BREATH_NEW = 0x08;
	final static int MONITOR_ALARM_RESP = 0x10;
	final static int MONITOR_ALARM_ECG = 0x20;
	final static int MEDIAN_RELEARN = 0x40;
	// Per Sample Flags for ECG --> definition of EcgInFlags--------------<<<<<<<<
	// 13 ECG BYTES-----------------------------------------------
	final static int IN_ECG5_PARA = 0;
	final static int IN_ECG5_WAVE1_S0 = 1;
	final static int IN_ECG5_WAVE1_S1 = 2;
	final static int IN_ECG5_WAVE1_S2 = 3;
	final static int IN_ECG5_WAVE2_S0 = 4;
	final static int IN_ECG5_WAVE2_S1 = 5;
	final static int IN_ECG5_WAVE2_S2 = 6;
	final static int IN_ECG5_WAVE3_S0 = 7;
	final static int IN_ECG5_WAVE3_S1 = 8;
	final static int IN_ECG5_WAVE3_S2 = 9;
	final static int IN_ECG5_FLAGS_LSB = 10;
	final static int IN_ECG5_FLAGS_MSB = 11;
	final static int IN_ECG5_RESP_WAVE = 12;
	// final static int IN_ECG5_BYTES = 13;
	final static int IN_ECG5_BYTES = 16;
	// 4 BYTES
	final static int FRAME_NUM = 0;
	final static int MOD_BITS_LSB = 1;
	final static int MOD_BITS_MSB = 2;
	final static int QUICK_COMMAND = 3;
	final static int FRAME_BYTES = 4;

	// PATIENT_ID_MODULE :- PtID(4 bytes), Name(20 chars), BedNum, Control Commands
	// PACKET STRUCTURE----------------------------------------
	final static int NO_MODULE_BIT = 1;
	final static int ECG_5L_BIT = 2;
	final static int SPO2_BIT = 4;
	final static int NIBP_BIT = 8;
	final static int IBP1_BIT = 16;
	final static int IBP2_BIT = 32;
	final static int TEMP_BIT = 64;
	final static int ETCO2_BIT = 128; // 1
	final static int AGM_BIT = (1 << 8); // 2
	final static int CO_BIT = (2 << 8); // 4
	final static int DOA_BIT = (4 << 8); // 8
	final static int PT_INFO_BIT = (8 << 8); // 16
	final static int ECG_3L_BIT = (16 << 8); // 32;
	final static int AVG_COMPLEX_BIT = (32 << 8); // 64
	final static int NEW_MODULE_BIT = (64 << 8); // 128

	// PER SECOND PARA FOR ECG---------------------------------
	final static int ECG_HR = 0;
	final static int RESP_TEMP_CH = 1;
	final static int BREATH_RATE = 2;
	final static int RESP_APNEA = 3;
	final static int TEMP_CH1_LSB = 4;
	final static int TEMP_CH1_MSB = 5;
	final static int TEMP_CH2_LSB = 6;
	final static int TEMP_CH2_MSB = 7;
	final static int TEMP_FLAGS = 8;
	final static int ECG_AYSTOLE = 9;
	final static int ECG_IRREGULAR_RYTHEM = 10;
	final static int ECG_BRADYCARDIA = 11;
	final static int ECG_TACHYCARDIA = 12;
	final static int ECG_WIDE_QRS = 13;
	final static int ECG_SW_VERSION = 14;
	final static int ECG_IMP_L1 = 15;
	final static int ECG_IMP_L2 = 16;
	final static int ECG_IMP_V = 17;
	final static int ECG_DET_LEAD = 18;
	final static int T1_CAL_VAL_IN = 19;
	final static int T2_CAL_VAL_IN = 20;
	final static int ECG_HR_EX = 21;
	final static int ECG_RECOBRO_PS = 22;
	final static int ECG_LAST_BYTE = 23;

	// SpO2 module definition---------------------------->>>>>>>>>>>>>>>>

	final static int SPO2_PR = 0;
	final static int SPO2_O2 = 1;
	final static int SPO2_PERF_LSB = 2;
	final static int SPO2_PERF_MSB = 3;
	final static int SPO2_RED_PWM = 4;
	final static int SPO2_IRED_PWM = 5;
	final static int SPO2_RED_LEVEL = 6;
	final static int SPO2_IRED_LEVEL = 7;
	final static int SPO2_FLAGS = 8;
	final static int SPO2_BAR_GRAPH = 9;
	final static int SPO2_LAST_BYTE = 10;
	// SpO2 module definition----------------------------<<<<<<<<<<<<<<<<<<

	final static int TEMP_T1_LSB = 0;
	final static int TEMP_T1_MSB = 1;
	final static int TEMP_T2_LSB = 2;
	final static int TEMP_T2_MSB = 3;
	final static int T1_T2_FLAGS = 4;
	final static int TEMP_LAST_BYTE = 5;

	final static int NIBP_HR = 0;
	final static int NIBP_SYSTOLE = 1;
	final static int NIBP_DIASTOLE = 2;
	final static int MAP_NIBP = 3;
	final static int NIBP_RX_FLAGS = 4;
	final static int NIBP_SW_VERSION = 5;
	final static int NIBP_LAST_BYTE = 6;

	final static int IBP_CH1_HR = 0;
	final static int TEMP_CH = 1;
	final static int IBP_CH1_SYSTOLE_LSB = 2;
	final static int IBP_CH1_SYSTOLE_MSB = 3;
	final static int IBP_TEMP_CH1_LSB = 4;
	final static int IBP_TEMP_CH1_MSB = 5;
	final static int IBP_TEMP_CH2_LSB = 6;
	final static int IBP_TEMP_CH2_MSB = 7;
	final static int IBP_TEMP_FLAGS = 8;
	final static int IBP_CH1_DIASTOLE_LSB = 9;
	final static int IBP_CH1_DIASTOLE_MSB = 10;
	final static int IBP_CH1_MAP_LSB = 11;
	final static int IBP_CH1_MAP_MSB = 12;
	final static int IBP_CH2_HR = 13;
	final static int IBP_CH2_SYSTOLE_LSB = 14;
	final static int IBP_CH2_SYSTOLE_MSB = 15;
	final static int IBP_CH2_DIASTOLE_LSB = 16;
	final static int IBP_CH2_DIASTOLE_MSB = 17;
	final static int IBP_CH2_MAP_LSB = 18;
	final static int IBP_CH2_MAP_MSB = 19;
	final static int IBP_RX_FLAGS = 20;
	final static int IBP_CH1_SW_VERSION = 21;
	final static int IBP_CH1_VOL_IND = 22;
	final static int IBP_CH2_VOL_IND = 23;
	final static int IBP_CH12_LABEL_IND = 24;
	final static int IBP_LAST_BYTE = 25;

	// Sensor error, NewBeat-------------------------->>>>>>>>>>>
	final static int NEW_BEAT_IBP_CH1 = 0x01;
	final static int NEW_BEAT_IBP_CH2 = 0x02;
	final static int SENSOR_ERROR_IBP_CH1 = 0x04;
	final static int SENSOR_ERROR_IBP_CH2 = 0x08;
	final static int IBP_CH1_WAVE1_MSBIT = 0x40;
	final static int IBP_CH2_WAVE2_MSBIT = 0x80;
	// Sensor error, NewBeat--------------------------<<<<<<<<

	// EtCo2 Module Per sec Para ----------------->>>>>>>>>>>>>>>>>>>>

	final static int CO2_ETCO2 = 0;
	final static int CO2_ICO2 = 1;
	final static int CO2_RESP = 2;
	final static int CO2_RX_FLAGS = 3;
	final static int CO2_LAST_BYTE = 4;

	// Bit values for Co2RxFlagsPerSample------------->>>>>>>>>>>>>
	final static int CO2_NEW_BREATH = 0x01;
	final static int CO2_WATER_TRAP_ERR = 0x02;
	final static int CO2_OCCLUSION_ERR = 0x04;
	final static int CO2_MODULE_ERR = 0x08;
	final static int CO2_APNEA = 0x10;
	final static int CO2_NO_SAMPLE_LINE = 0x20;
	// Bit values for Co2RxFlagsPerSample-------------<<<<<<<<<<<<

	// Per Second flags for ECG --> definition of EcgInSecFlags-------------->>>>>>
	final static int PS_ECG_AYSTOLE = 0x01;
	final static int PS_ECG_IRR_RYTHEM = 0x02;
	final static int PS_ECG_BRADY = 0x04;
	final static int PS_ECG_TACHY = 0x08;
	final static int PS_ECG_WIDE_QRS = 0x10;
	final static int PS_RESP_APNEA = 0x20;
	// Per Second flags for ECG --> definition of EcgInSecFlags--------------<<<<<<<

	static final int HR_ECG = 0;
	static final int HR_IBP1 = 1;
	static final int HR_IBP2 = 2;
	static final int HR_IBP3 = 3;
	static final int HR_IBP4 = 4;
	static final int HR_SPO2 = 5;
	static final int HR_NIBP = 6;

	// 4 SPO2 BYTES ---------------------------------------
	final static int IN_SPO2_PARA = 0;
	final static int IN_SPO2_WAVE1_LSB = 1;
	final static int IN_SPO2_BAR_GRAPH = 2;
	final static int IN_SPO2_FLAGS = 3;
	final static int IN_SPO2_BYTES = 4;
	// -------------- SPO2Flags Bits Definition Start----------------->>>>>>>>>
	// 3 NIBP BYTES ------------------------------------------
	final static int IN_NIBP_PARA = 0;
	final static int IN_CUFF_PRESSURE_LSB = 1;
	final static int IN_CUFF_PRESSURE_MSB = 2;
	// final static int IN_NIBP_BYTES = 3;
	final static int IN_NIBP_BYTES = 5;

	// 5 IBP1 BYTES -------------------------------------------
	final static int IN_IBP1_PARA = 0;
	final static int IN_IBP1_WAVE1_LSB = 1;
	final static int IN_IBP1_WAVE2_LSB = 2;
	final static int IN_IBP1_MSB = 3;
	final static int IN_IBP1_FLAGS = 4;
	final static int IN_IBP1_BYTES = 5;
	final static int IN_IBP_LBL_INDEX = 22; // SAME FOR IBP1 & IBP2

	// IBP_RX_FLAGS per sec-------------------------->>>>>>>>>>>>>>
	final static int STATIC_IBP_CH1 = 0x01;
	final static int STATIC_IBP_CH2 = 0x02;
	final static int MONITOR_ALARM_IBP_CH1 = 0x04;
	final static int MONITOR_ALARM_IBP_CH2 = 0x08;
	// IBP_RX_FLAGS per sec--------------------------<<<<<<<<<<<<

	// 5 IBP2 BYTES -------------------------------------------
	final static int IN_IBP2_PARA = 0;
	final static int IN_IBP12_WAVE1_LSB = 1;
	final static int IN_IBP12_WAVE2_LSB = 2;
	final static int IN_IBP12_MSB = 3;
	final static int IN_IBP2_FLAGS = 4;
	final static int IN_IBP2_BYTES = 5;

	// 1 TEMP BYTE ----------------------------------------------
	final static int IN_TEMP_PARA = 0;
	final static int IN_TEMP_BYTES = 1;

	// 3 ETCO2 BYTES --------------------------------------------
	final static int IN_ETCO2_PARA = 0;
	final static int IN_ETCO2_WAVE1_LSB = 1;
	final static int IN_ETCO2_FLAGS = 2;
	final static int IN_ETCO2_BYTES = 3;

	final static int NEW_BEAT_SPO2 = 0x01;
	final static int OPEN_FINGER = 0x02;
	final static int NO_PULSE_ALARM = 0x04;
	final static int SEARCHING_PULSE = 0x08;
	final static int LOW_PERFUSION = 0x10;
	final static int NO_PROBE = 0x20;
	final static int START_MONITORING_ALARM = 0x40;
	// -------------- SPO2Flags Bits Definition Start-----------------<<<<<<<<<<<
	// Defn of NiBPRxFlags
	final static byte BP_COMPLETE = (byte) 0x01;
	final static byte NBP_NEW_BEAT = (byte) 0x02; // Actual coming with cuff pressure @per sample
	final static byte NBP_CUFF_ERROR = (byte) 0x04;
	final static byte NBP_TIME_OUT = (byte) 0x08;
	final static byte NBP_MEASURE_ERROR = (byte) 0x10;
	final static long BP_IN_PROCESS = 0x00020000;
	// Adding to Nibp Tx Flags Air_Leak, Air_Block, Weak_Pulse
	final static byte NBP_AIR_LEAK = (byte) 0x20;
	final static byte NBP_AIR_BLOCK = (byte) 0x40;
	final static byte NBP_WEAK_PULSE = (byte) 0x80;

	// INVALID VALUES-----------------------------------------
	final static int IBP_INVALID_VAL = 301;
	final static int CO2_INVALID_VAL = 255;
	final static int ST_INVALID_VAL = 255;
	final static int TEMP_INVALID_VAL = 0;
	final static int SPO2_INVALID_VAL = 0;
	final static int HR_INVALID_VAL = 0;
	final static int RESP_INVALID_VAL = 0;
	final static int NIBP_INVALID_VAL = 0;

	static int FrameNum;
	private int testCounter = 0;
	private static final int BT_SAMP_COUNTER = 30;
	public static String BATTERY_MSG = "";
	public static int RECOBRO_POWER_STAT;
	byte[] NCMSParaArr = new byte[39 + 17];
	final static int RESP_MODULE_PRESENT = 0x80;
	final static int ECG_TEMP_MODULE_PRESENT = 0x03;
	private static final int ECGGainFactor = 1;
	private static final int IbpGainFactor = 1;
	private static final int CO2GainFactor = 1;
	public static int hrSource = HR_NIBP; // DEFAULT HR SOURCE------------
	private int SpO2_DataIn[] = new int[SPO2_LAST_BYTE];
	private int TEMP_DataIn[] = new int[TEMP_LAST_BYTE];
	private int NIBP_DataIn[] = new int[NIBP_LAST_BYTE];
	private int IBP_DataIn[] = new int[IBP_LAST_BYTE];
	private int ECG_DataIn[] = new int[ECG_LAST_BYTE];
	private int CO2_DataIn[] = new int[CO2_LAST_BYTE];
	static byte tECGFlagBits, tSpO2FlagBits, tIBP1FlagBits, tIBP2FlagBits, tIBP3FlagBits, tEtCO2FlagBits, tNiBPFlagBits;
	public static String WaveError[] = AppCons.DEFAULT_WAVE_ERROR;
	public static int ECGWaveIndex = 0;
	static int FLG_MASK[][] = { { 0x80, 0x100, 0x200 }, { 0x400, 0x800, 0x1000 }, { 0x2000, 0x4000, 0x8000 } };
	float O2_Wave = 0, O2_Perf = 0, O2_Val = 0, O2_PR = 0, O2_Bar = 0;
	private boolean isNibpProcessing;
	
	int[] wavearray = new int[1000];

	public int count = 0;
	public int file_num = 0;
	
	private File file;
	public PacketResolver() {

	}

	public void Resolve(byte[] outBuf) {
		int temp = outBuf[MOD_BITS_LSB] | (outBuf[MOD_BITS_MSB] << 8);
		int ModOffset = FRAME_BYTES; // BytePosition
		FrameNum = outBuf[0];

		if ((temp & NO_MODULE_BIT) > 0) {

			ModOffset += 0;
		}
		if ((temp & ECG_5L_BIT) > 0) {
			Ecg5L_Module(ModOffset, outBuf);
			ModOffset += IN_ECG5_BYTES;
		}
		if ((temp & SPO2_BIT) > 0) {

			SPO2_Module(ModOffset, outBuf);
			ModOffset += IN_SPO2_BYTES;
		}
		if ((temp & NIBP_BIT) > 0) {
			NIBP_Module(ModOffset, outBuf);
			ModOffset += IN_NIBP_BYTES;
		}
		if ((temp & IBP1_BIT) > 0) {
			IBP1_Module(ModOffset, outBuf);
			ModOffset += IN_IBP1_BYTES;
		}
		if ((temp & IBP2_BIT) > 0) {
			IBP2_Module(ModOffset, outBuf);
//			ModOffset += IN_IBP2_BYTES;
		}
		if ((temp & TEMP_BIT) > 0) {
			TEMP_Module(ModOffset, outBuf);
			ModOffset += IN_TEMP_BYTES;
		}
		if ((temp & ETCO2_BIT) > 0) {
			ETCO2_Module(ModOffset, outBuf);
			ModOffset += IN_ETCO2_BYTES;
		}
//		if ((temp & AGM_BIT) > 0) {
//			AGM_Module(ModOffset, outBuf);
//			ModOffset += IN_AGM_BYTES;
//		}
//		if ((temp & CO_BIT) > 0) {
//			CO_Module(ModOffset, outBuf);
//			ModOffset += IN_CO_BYTES;
//		}
//		if ((temp & DOA_BIT) > 0) {
//			DOA_Module(ModOffset, outBuf);
//			ModOffset += IN_DOA_BYTES;
//		}
//		if ((temp & PT_INFO_BIT) > 0) {
//			PT_INFO_Module(ModOffset, outBuf);
//			// Log.e(TAG+" PT_INFO_BIT "," > 0 ");
//
//			isRecobro = false;
//			ModOffset += IN_PT_INFO_BYTES;
//		} else {
//			// Log.e(TAG+" PT_INFO_BIT "," < 0 ");
//			isRecobro = true;
//			PT_INFO_Rocobro();
//			ModOffset += IN_PT_INFO_BYTES;
//		}
//		if ((temp & ECG_3L_BIT) > 0) {
//			ECG_3L_Module(ModOffset, outBuf);
//			ModOffset += IN_ECG3_BYTES;
//		}
//		if ((temp & AVG_COMPLEX_BIT) > 0) {
//			AVG_COMPLEX_Module(ModOffset, outBuf);
//			ModOffset += IN_AVG_COMPLEX_BYTES;
//		}
//		if ((temp & NEW_MODULE_BIT) > 0)
//			NEW_MODULE_Module(ModOffset, outBuf);
//		if (isRecobro) {
//			setRecobroHrSrc();
//		}
	}

	private void SPO2_Module(int ModOffset, byte[] outBuf) {

		int O2_ParaFlags = 0, d0;

		if (FrameNum < (SPO2_LAST_BYTE - 1)) {
			SpO2_DataIn[FrameNum] = outBuf[ModOffset + IN_SPO2_PARA];
		} else if (FrameNum == (SPO2_LAST_BYTE - 1)) {
			O2_Perf = ((SpO2_DataIn[SPO2_PERF_MSB] << 8) | SpO2_DataIn[SPO2_PERF_LSB]);
			O2_Perf = O2_Perf / 100;
			O2_Val = SpO2_DataIn[SPO2_O2]; // SPO2_Para[O2].ena = 1;
			O2_PR = SpO2_DataIn[SPO2_PR]; // SPO2_Para[PR].ena = 1;
			/*//NSB
			System.out.println("O2 HR = " + (byte) O2_PR);
			System.out.println("O2 Val = " + (byte) O2_Val);
			System.out.println("O2 MSB = " + (byte) SpO2_DataIn[SPO2_PERF_MSB]);
			System.out.println("O2 LSB = " + (byte) SpO2_DataIn[SPO2_PERF_LSB]);
			*/
		}
		
		// FEED PARACENTRE ARRAY------------------------------------------
//    paraCentre[SPO2_BOX_LBL] = "SPO2";
		if (O2_Val == SPO2_INVALID_VAL) {
//    	 System.out.println("Spo2 Invalid---%");
//        paraCentre[SPO2_O2_VAL] = "---";
//        paraCentre[SPO2_PERF_VAL] = "--- %";
//        CURRENT_ALARMS[SPO2_O2_ALARM] = ALARM_OFF;
		} else {
//        paraCentre[SPO2_O2_VAL] = ((int) O2_Val) + "";
			if (count % 60 == 0 ) //NSB
			{
				System.out.println("Spo2 HR -- " + O2_PR + " Perf --" + O2_Val + "%");
			}

		}

		if (hrSource == HR_SPO2) {
			if (O2_PR == HR_INVALID_VAL) {
				System.out.println("O2 Hr ---");
			} else {
				System.out.println("O2 Hr " + O2_PR);
			}
		

		O2_Bar = (float) (outBuf[ModOffset + IN_SPO2_BAR_GRAPH] * 0.6);
		if (O2_Bar > 50)
			O2_Bar = 50;
		O2_ParaFlags = outBuf[ModOffset + IN_SPO2_FLAGS];

//    if ((O2_ParaFlags & NEW_BEAT_SPO2) > 0) {
//        isHeartBeat = true;
//        context.sendBroadcast(hBeatIntent);
//    } else {
		testCounter++;
		if (testCounter == BT_SAMP_COUNTER) {
//            isHeartBeat = false;
			testCounter = 0;
//        }
		}


			if (((O2_ParaFlags & OPEN_FINGER) > 0)) {
				//NSB System.out.println("SPO2 SENSOR ERROR");
	//        isSPO2Present = false;
	
			} else if ((O2_ParaFlags & SEARCHING_PULSE) > 0) {
				System.out.println("SPO2 SEARCHING PULSE");
	//        isSPO2Present = true;
			} else {
	//        WaveError[WAVE_SPO2] = "";
			}
		}
		/*
		 * Wave Spo2 value in @param d0
		 */

		d0 = outBuf[ModOffset + IN_SPO2_WAVE1_LSB];
		if (d0 > 127)
			d0 -= 256;
 
	  if ( count == 0 )
	  {
		file = new File("/tmp/tfile" + file_num + ".txt");
		file_num += 1;
		//Create the file
		try {
			if (file.createNewFile()){
			System.out.println("File is created!");
			}else{
			System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		  
	  }
      wavearray[count++] = d0;
      if (count % 67 == 0) {
    	  for (int i=0; i < 67; i++) {
    	      System.out.printf("0x%x ", wavearray[i]);
    	      //Write Content
    	  }
      }
      
      if (count >= 670 )	
      {
    	    count = 0;
    	    FileWriter writer;
			try {
	      		writer = new FileWriter(file, true);
		      	for (int i=0; i < 670; i++) {
					writer.write(String.format("%d\n", wavearray[i]));
		    	}
	            writer.close();
		     
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }	
      
//    Wave[WAVE_SPO2][0] = Wave[WAVE_SPO2][2] + (d0 - Wave[WAVE_SPO2][2]) / 3;
//    Wave[WAVE_SPO2][1] = Wave[WAVE_SPO2][0] + (d0 - Wave[WAVE_SPO2][2]) / 3;
//    Wave[WAVE_SPO2][2] = d0;

	}

	private void Ecg5L_Module(int ModOffset, byte[] outBuf) {
		int T1ValIn = 0, T2ValIn = 0, EcgHr = 0, EcgSoftVersion = 0, Ecg_Imp_L1 = 0, Ecg_Imp_L2 = 0, Ecg_Imp_V = 0,
				Ecg_Det_Lead = 0;
		int Ecg_Breath_rate = 0, Ecg_Resp_Br = 0, k = 0, d0 = 0, d1 = 0, Ecg_T1 = 0, Ecg_T2 = 0;
		double Temp_Ch1_A = 0, Temp_Ch2_A = 0;
		int TempRxFlagsPerSec = 0, P_T1_T2_Flags = 0, EcgInSecFlags = 0, EcgInFlags = 0, dL1 = 0, dL2 = 0, dL3 = 0,
				dV = 0;
		int EcgInBuf[][] = new int[7][3];
		final int L1 = 0, L2 = 1, V = 2, L3 = 3, AVR = 4, AVL = 5, AVF = 6;
		int temp;
		EcgInFlags = (outBuf[ModOffset + IN_ECG5_FLAGS_MSB] << 8) + outBuf[ModOffset + IN_ECG5_FLAGS_LSB];
		// Log.e(TAG+" PM_PULSE1 ",(EcgInFlags & PM_PULSE)+"--");
		AppCons.PACEMAKER_DRAW = (EcgInFlags & PM_PULSE);
		AppCons.BREATH_DRAW = (EcgInFlags & BREATH_NEW);

		if ((EcgInFlags & BREATH_NEW) > 0) {
			System.out.println(" BREATH_NEW " + (EcgInFlags & BREATH_NEW) + "--");

		}

		if ((EcgInFlags & NEW_BEAT_ECG) > 0) {
			// isHeartBeat = true;
			// context.sendBroadcast(hBeatIntent);
		} else {
			testCounter++;
			if (testCounter == BT_SAMP_COUNTER) {
				// isHeartBeat = false;
				testCounter = 0;
			}
		}

		if (FrameNum < (ECG_LAST_BYTE)) {
			ECG_DataIn[FrameNum] = outBuf[ModOffset + IN_ECG5_PARA];

		} else if (FrameNum == (ECG_LAST_BYTE)) // Draw Parameters at appropriate location;
		{
			T1ValIn = ECG_DataIn[T1_CAL_VAL_IN];
			T2ValIn = ECG_DataIn[T2_CAL_VAL_IN];
			EcgHr = ECG_DataIn[ECG_HR] + ECG_DataIn[ECG_HR_EX];
			Ecg_Breath_rate = ECG_DataIn[BREATH_RATE];
			RECOBRO_POWER_STAT = (ECG_DataIn[ECG_RECOBRO_PS]);
			BATTERY_MSG = "";
			if ((RECOBRO_POWER_STAT == 0x10)) {
				BATTERY_MSG = "Battery Hot Swap Mode";
			} else if ((RECOBRO_POWER_STAT == 0x02)) {
				BATTERY_MSG = "Low Battery";
			} else if ((RECOBRO_POWER_STAT == 0x04)) {
				BATTERY_MSG = "Critical Low Battery";
			} else {
				BATTERY_MSG = "";
				RECOBRO_POWER_STAT = 0;
			}

			NCMSParaArr[AppCons.NCMS_ECG_HR] = (byte) EcgHr;
			NCMSParaArr[AppCons.NCMS_ECG_BR] = (byte) Ecg_Breath_rate;

			EcgSoftVersion = ECG_DataIn[ECG_SW_VERSION];
			Ecg_Imp_L1 = ECG_DataIn[ECG_IMP_L1];
			Ecg_Imp_L2 = ECG_DataIn[ECG_IMP_L2];
			Ecg_Imp_V = ECG_DataIn[ECG_IMP_V];
			Ecg_Det_Lead = ECG_DataIn[ECG_DET_LEAD];
			k = (ECG_DataIn[RESP_TEMP_CH] & ECG_TEMP_MODULE_PRESENT);
			k = 1; // MANUALLY ADDED LINE OF CODE-----

			if (hrSource == HR_ECG) {
				if (EcgHr == HR_INVALID_VAL) {
					//NSB System.out.println("---");

				} else {
					System.out.println(EcgHr + "");
	
				}
			}
			// PRE_IS_ECG_RESP = isECGRESPPresent;
			if (Ecg_Breath_rate == RESP_INVALID_VAL) {
				//NSB System.out.println("---");
		

			} else {
		
				System.out.println(Ecg_Breath_rate + "");
			
			}

			if (k > 0) {

				d0 = ECG_DataIn[TEMP_CH1_LSB];
				d1 = ECG_DataIn[TEMP_CH1_MSB];
				NCMSParaArr[AppCons.NCMS_TEMP1_LSB] = (byte) d0;
				NCMSParaArr[AppCons.NCMS_TEMP1_MSB] = (byte) d1;

				Ecg_T1 = (d1 << 8) + d0;
				Temp_Ch1_A = d0 + (d1 << 8);
				Temp_Ch1_A = Temp_Ch1_A / 10;
				if (Temp_Ch1_A == TEMP_INVALID_VAL) {
					//NSB System.out.println("---");
				
				} else {

			
					System.out.println(Temp_Ch1_A + "");
				
				}

				d0 = ECG_DataIn[TEMP_CH2_LSB];
				d1 = ECG_DataIn[TEMP_CH2_MSB];
				NCMSParaArr[AppCons.NCMS_TEMP2_LSB] = (byte) d0;
				NCMSParaArr[AppCons.NCMS_TEMP2_MSB] = (byte) d1;
				Ecg_T2 = (d1 << 8) + d0;
				Temp_Ch2_A = d0 + (d1 << 8);
				Temp_Ch2_A = Temp_Ch2_A / 10;
				if (Temp_Ch2_A == TEMP_INVALID_VAL) {
					//NSB System.out.println("---");
					// paraCentre[TEMP_T2_VAL] = "---";
					// CURRENT_ALARMS[TEMP_T2_ALARM] = ALARM_OFF;
				} else {
				
					System.out.println(Temp_Ch2_A + "");
				
				}
			}

			EcgInSecFlags = (ECG_DataIn[ECG_AYSTOLE] | (ECG_DataIn[ECG_IRREGULAR_RYTHEM] << 1)
					| (ECG_DataIn[ECG_BRADYCARDIA] << 2) | (ECG_DataIn[ECG_TACHYCARDIA] << 3)
					| (ECG_DataIn[ECG_WIDE_QRS] << 4) | (ECG_DataIn[RESP_APNEA] << 5));
			tECGFlagBits = 0x01;
			tECGFlagBits |= (EcgInSecFlags << 1);
			// WaveError[ECGWaveIndex] = "";
			// for (int i = 0; i < WavesToDisp; i++) {
			// temp = WaveInd[i];
			// if (WaveInd[i] == WAVE_L1 || WaveInd[i] == WAVE_L3 || WaveInd[i] ==
			// WAVE_CHEST || WaveInd[i] == WAVE_AVF
			// || WaveInd[i] == WAVE_AVL || WaveInd[i] == WAVE_AVR || WaveInd[i] == WAVE_L2)
			// {
			// ECGWaveIndex = temp;
			// break;
			// }
			// }
			/*
			 * EcgInFlags = (outBuf[ModOffset + IN_ECG5_FLAGS_MSB] << 8) + outBuf[ModOffset
			 * + IN_ECG5_FLAGS_LSB];
			 * 
			 * // Log.e(TAG+" RESP_APNEA ",(ECG_DataIn[RESP_APNEA]+"")); //
			 * Log.e(TAG+" ECG_BRADYCARDIA ",(ECG_DataIn[ECG_BRADYCARDIA]+""));
			 * Log.e(TAG+" PM_PULSE1 ",(EcgInFlags & PM_PULSE)+"--"); if ((EcgInFlags &
			 * PM_PULSE) > 0) { Log.e(TAG+" PM_PULSE ",(EcgInFlags & PM_PULSE)+"--"); } if
			 * ((EcgInFlags & BREATH_NEW) > 0) { Log.e(TAG+" BREATH_NEW ",(EcgInFlags &
			 * BREATH_NEW)+"--"); }
			 */
			/*
			 * else { Log.e(TAG+" PM_PULSE ","No pacemaker"); }
			 */

			if ((EcgInFlags & LEAD_OPEN_ECG) > 0) {
				WaveError[ECGWaveIndex] = "ECG LEAD OPEN";
				// isECGPresent = false;

			} else {
				WaveError[ECGWaveIndex] = "";
				// isECGPresent = true;
				if ((EcgInSecFlags & PS_ECG_AYSTOLE) > 0) {
					WaveError[ECGWaveIndex] = "ECG ASYSTOLE";

				} else if ((EcgInSecFlags & PS_ECG_IRR_RYTHEM) > 0) {
					WaveError[ECGWaveIndex] = "ECG IRR RHYTHM";
				} else if ((EcgInSecFlags & PS_ECG_BRADY) > 0) {
					WaveError[ECGWaveIndex] = "ECG BRADYCARDIA";
					// Log.e(TAG + " ECG_BRADYCARDIA ", "YES GOING----");

				} else if ((EcgInSecFlags & PS_ECG_TACHY) > 0) {

					WaveError[ECGWaveIndex] = "ECG TACHYCARDIA";

				} else if ((EcgInSecFlags & PS_ECG_WIDE_QRS) > 0) {
					WaveError[ECGWaveIndex] = "ECG WIDE QRS";
				}
				if ((EcgInSecFlags & PS_RESP_APNEA) > 0) {
					System.out.println("RESP APNEA");
				} else {
					// WaveError[WAVE_RESP] = "";
				}

			}
			// Log.e(TAG+" ECG_BRADYCARDIA ",(ECG_DataIn[ECG_BRADYCARDIA])+"---");

		}
		/*
		 * Wave ECG value 3 ECG samples @67 Hz
		 */
		for (int i = 0; i < 3; i++)// 3 ECG samples @67 Hz

		{
			if ((EcgInFlags & LEAD_OPEN_ECG) > 0) {
				dL1 = 0;
				dL2 = 0;
				dV = 0; // Clear the lead traces
			} else {
				d0 = outBuf[ModOffset + IN_ECG5_WAVE1_S0 + i];
				if ((EcgInFlags & FLG_MASK[0][i]) > 0)
					d0 = -d0;
				d0 = (int) (d0 * ECGGainFactor);
				dL1 = (d0);

				d0 = outBuf[ModOffset + IN_ECG5_WAVE2_S0 + i];
				if ((EcgInFlags & FLG_MASK[1][i]) > 0)
					d0 = -d0;
				d0 = (int) (d0 * ECGGainFactor);
				dL2 = (d0);

				d0 = outBuf[ModOffset + IN_ECG5_WAVE3_S0 + i];
				if ((EcgInFlags & FLG_MASK[2][i]) > 0)
					d0 = -d0;
				d0 = (int) (d0 * ECGGainFactor);
				dV = (d0);

			}

			dL3 = dL2 - dL1;

			// Log.e(TAG + " dL1 ", dL1 + "");
			EcgInBuf[L1][i] = dL1;
			EcgInBuf[L2][i] = dL2;
			EcgInBuf[V][i] = dV;

			EcgInBuf[L3][i] = dL3;
			EcgInBuf[AVR][i] = -((dL1 + dL2) >> 1);
			EcgInBuf[AVL][i] = ((dL1 - dL3) >> 1);
			EcgInBuf[AVF][i] = ((dL2 + dL3) >> 1);

			// Log.e(TAG+" NCMS_NEW_BEAT_DETECT ",(flags & NCMS_NEW_BEAT_DETECT)+" ");

		}

		// if (isECGPresent) { // If ECG is present then only Resp will be present
		/*
		 * Wave RESP value in @param d0 sample @67 Hz
		 */
		d0 = outBuf[ModOffset + IN_ECG5_RESP_WAVE];// @67 Hz
		if (d0 > 127)
			d0 -= 256;
		// Wave[WAVE_RESP][0] = Wave[WAVE_RESP][2] + (d0 - Wave[WAVE_RESP][2]) / 3;
		// Wave[WAVE_RESP][1] = Wave[WAVE_RESP][0] + (d0 - Wave[WAVE_RESP][2]) / 3;
		// Wave[WAVE_RESP][2] = d0;
		// // WaveError[WAVE_RESP] = "";
		//
		// } else {
		// WaveError[WAVE_RESP] = "RESP LEAD OPEN";
		// }

	}

	
	private void IBP2_Module(int ModOffset, byte[] outBuf) {
	}

	private void IBP1_Module(int ModOffset, byte[] outBuf) {
		int d0, d1, ibpHr = 0;
		int ch1Sys, ch2Sys, ch3Sys, ch4Sys;
		int ch1Dia, ch2Dia, ch3Dia, ch4Dia;
		int Ibp1_S_Lsb, Ibp1_S_Msb;
		int Ibp1_D_Lsb, Ibp1_D_Msb;
		int Ibp1_M_Lsb, Ibp1_M_Msb;
		int Ibp1_Ch1_Map, Ibp1_Ch2_Map;
		int tempFlag1 = 0, tempFlag2 = 0, tempFlagRx = 0;

		if (FrameNum < IBP_LAST_BYTE) {
			IBP_DataIn[FrameNum] = outBuf[IN_IBP1_PARA + ModOffset];
		} else if (FrameNum == IBP_LAST_BYTE) {
			// IBP1 PACKET DATA-----------------------------------------
			d0 = IBP_DataIn[IBP_CH1_SYSTOLE_LSB];
			d1 = IBP_DataIn[IBP_CH1_SYSTOLE_MSB];
			ch1Sys = d0 + (d1 << 8);
			d0 = IBP_DataIn[IBP_CH1_DIASTOLE_LSB];
			d1 = IBP_DataIn[IBP_CH1_DIASTOLE_MSB];
			ch1Dia = d0 + (d1 << 8);
			d0 = IBP_DataIn[IBP_CH1_MAP_LSB];
			d1 = IBP_DataIn[IBP_CH1_MAP_MSB];
			Ibp1_Ch1_Map = d0 + (d1 << 8);
			// paraCentre[IBP1_BOX_LBL] = "IBP1";

			// CHECK FOR INVALID VALUES---------------------------------
			String ibp1Val = "";

			if (Ibp1_Ch1_Map == IBP_INVALID_VAL) {
				//NSB System.out.println("Ibp1 val--");
				// paraCentre[IBP1_MAP_VAL] = "---";
			} else {
				//NSB System.out.println("Ibp1 val--" + Ibp1_Ch1_Map);
				// paraCentre[IBP1_MAP_VAL] = Ibp1_Ch1_Map + "";
			}

			if ((ch1Sys == IBP_INVALID_VAL || ch1Sys == 0) || (ch1Dia == IBP_INVALID_VAL || ch1Dia == 0)) {
				ibp1Val = "---" + " / " + "---";
			} else {
				ibp1Val = ch1Sys + " / " + ch1Dia;
			}
			//NSB System.out.println("Ibp1 val--" + ibp1Val);
			// paraCentre[IBP1_VAL] = ibp1Val;

			// IBP2 PACKET DATA------------------------------

			ibpHr = IBP_DataIn[IBP_CH1_HR];
			d0 = IBP_DataIn[IBP_CH2_SYSTOLE_LSB];
			d1 = IBP_DataIn[IBP_CH2_SYSTOLE_MSB];
			ch2Sys = d0 + (d1 << 8);
			d0 = IBP_DataIn[IBP_CH2_DIASTOLE_LSB];
			d1 = IBP_DataIn[IBP_CH2_DIASTOLE_MSB];
			ch2Dia = d0 + (d1 << 8);
			d0 = IBP_DataIn[IBP_CH2_MAP_LSB];
			d1 = IBP_DataIn[IBP_CH2_MAP_MSB];
			Ibp1_Ch2_Map = d0 + (d1 << 8);
			// paraCentre[IBP2_BOX_LBL] = "IBP2";

			// Check for invalid values--------------
			String ibp2Val = "";
			if (Ibp1_Ch2_Map == IBP_INVALID_VAL) {
				// paraCentre[IBP2_MAP_VAL] = "---";
				//NSB System.out.println("Ibp2 val--");
			} else {
				//NSB System.out.println("Ibp2 val2--" + Ibp1_Ch2_Map);
				// paraCentre[IBP2_MAP_VAL] = Ibp1_Ch2_Map + "";
			}

			if (ch2Sys == IBP_INVALID_VAL || ch2Sys == 0 || ch2Dia == IBP_INVALID_VAL || ch2Dia == 0) {
				ibp2Val = "---" + " / " + "---";
			} else {
				ibp2Val = ch2Sys + " / " + ch2Dia;
			}
			//System.out.println("Ibp2 val--" + ibp2Val);

			tempFlagRx = IBP_DataIn[IBP_RX_FLAGS];
			if ((tempFlagRx & STATIC_IBP_CH1) > 0) {
				// WaveError[WAVE_IBP1] = "STATIC IBP";
				System.out.println("Ibp1 error--STATIC IBP");
			} else {
				// WaveError[WAVE_IBP1] = "";
			}
			if ((tempFlagRx & STATIC_IBP_CH2) > 0) {
				// WaveError[WAVE_IBP2] = "STATIC IBP";
				System.out.println("Ibp1 error--STATIC IBP");
			} else {
				// WaveError[WAVE_IBP2] = "";
			}
		}
		tempFlag1 = outBuf[ModOffset + IN_IBP1_FLAGS];
		if (hrSource == HR_IBP1) {
			if ((tempFlag1 & NEW_BEAT_IBP_CH1) > 0) {
				// isHeartBeat = true;
				// context.sendBroadcast(hBeatIntent);
			} else {
				testCounter++;
				if (testCounter == BT_SAMP_COUNTER) {
					// isHeartBeat = false;
					testCounter = 0;
				}
			}
		}
		tempFlag2 = outBuf[ModOffset + IN_IBP2_FLAGS];
		if (hrSource == HR_IBP2) {

			if ((tempFlag2 & NEW_BEAT_IBP_CH2) > 0) {
				// isHeartBeat = true;
				// context.sendBroadcast(hBeatIntent);
			} else {
				testCounter++;
				if (testCounter == BT_SAMP_COUNTER) {
					// isHeartBeat = false;
					testCounter = 0;
				}
			}
		}
		if ((tempFlag1 & SENSOR_ERROR_IBP_CH1) > 0) {
			//NSB System.out.println("IBP1 SENSOR ERROR");
			// isIBP1Present = false;
			hrSource = HR_IBP2;
		}
		if ((tempFlag2 & SENSOR_ERROR_IBP_CH2) > 0) {
			//NSB System.out.println("IBP2 SENSOR ERROR");

		}

		d0 = outBuf[ModOffset + IN_IBP12_WAVE1_LSB];
		d1 = outBuf[ModOffset + IN_IBP12_MSB];
		d0 = d0 + (d1 & 0x0F) * 256;
		d0 = (int) (d0 * IbpGainFactor);
		d0 = -d0;
		// channel Ht = 0.125*HeightMax ART 0 to 150, 0 to 250; ICP -10 to 10;

		// Wave[WAVE_IBP1][0] = (Wave[WAVE_IBP1][2] + (d0 - Wave[WAVE_IBP1][2]) / 3);
		// Wave[WAVE_IBP1][1] = (Wave[WAVE_IBP1][0] + (d0 - Wave[WAVE_IBP1][2]) / 3);
		// Wave[WAVE_IBP1][2] = d0;

		d0 = outBuf[ModOffset + IN_IBP12_WAVE2_LSB];
		d1 = outBuf[ModOffset + IN_IBP12_MSB];
		d0 = d0 + (d1 & 0xF0) * 16;
		d0 = (int) (d0 * IbpGainFactor);
		d0 = -d0;

		// Wave[WAVE_IBP2][0] = (Wave[WAVE_IBP2][2] + (d0 - Wave[WAVE_IBP2][2]) / 3);
		// Wave[WAVE_IBP2][1] = (Wave[WAVE_IBP2][0] + (d0 - Wave[WAVE_IBP2][2]) / 3);
		// Wave[WAVE_IBP2][2] = d0;

		if (hrSource == HR_IBP1) {
			// paraColor[CI_HR] = waveColor[WAVE_IBP1];
			if (ibpHr == HR_INVALID_VAL) {
				// paraCentre[HR_HRATE_VAL] = "---";
				// CURRENT_ALARMS[HR_HRATE_ALARM] = ALARM_OFF;
			} else {
				System.out.println("IBP1 Hr " + ibpHr);
				// paraCentre[HR_HRATE_VAL] = ibpHr + "";
				// if (ibpHr >= Integer.parseInt(SAVED_LIMITS[HR_HRATE_UL])
				// || ibpHr <= Integer.parseInt(SAVED_LIMITS[HR_HRATE_LL])) {
				// CURRENT_ALARMS[HR_HRATE_ALARM] = ALARM_ON;
				// } else {
				// CURRENT_ALARMS[HR_HRATE_ALARM] = ALARM_OFF;
				// }
			}
		}

		if (hrSource == HR_IBP2) {
			// paraColor[CI_HR] = waveColor[WAVE_IBP2];
			if (ibpHr == HR_INVALID_VAL) {
				// paraCentre[HR_HRATE_VAL] = "---";
				// CURRENT_ALARMS[HR_HRATE_ALARM] = ALARM_OFF;
			} else {
				System.out.println("IBP1 Hr " + ibpHr);

			}
		}

	}

	private void ETCO2_Module(int ModOffset, byte[] outBuf) {

		int d0, d1, i, tempFlag = 0, tempFlagPerSamp = 0;

		if (FrameNum < CO2_LAST_BYTE) {
			CO2_DataIn[FrameNum] = outBuf[IN_ETCO2_PARA + ModOffset];
		} else if (FrameNum == CO2_LAST_BYTE) // Draw Parameters at appropriate location;
		{

			if (CO2_DataIn[CO2_ETCO2] == CO2_INVALID_VAL) {
				// paraCentre[CO2_ET_VAL] = "---";
				// CURRENT_ALARMS[CO2_ET_ALARM] = ALARM_OFF;
			} else {
				// paraCentre[CO2_ET_VAL] = CO2_DataIn[CO2_ETCO2] + "";
				System.out.println("Co2 ET  " + CO2_DataIn[CO2_ETCO2]);

			}

			if (CO2_DataIn[CO2_ICO2] == CO2_INVALID_VAL) {
				// paraCentre[CO2_I_VAL] = "---";
				// CURRENT_ALARMS[CO2_I_ALARM] = ALARM_OFF;
			} else {
				System.out.println("Co2 ET  " + CO2_DataIn[CO2_ETCO2]);

			}
			if (CO2_DataIn[CO2_RESP] == CO2_INVALID_VAL) {
				// paraCentre[CO2_RESP_VAL] = "---";
				// CURRENT_ALARMS[CO2_RESP_ALARM] = ALARM_OFF;
			} else {
				System.out.println("Co2 RESP  " + CO2_DataIn[CO2_RESP]);

			}

		}

		tempFlagPerSamp = (outBuf[ModOffset + IN_ETCO2_FLAGS] & 0x3F);
		if ((tempFlagPerSamp & CO2_WATER_TRAP_ERR) > 0) {
			System.out.println("C02 WATER TRAP ERROR");
			// WaveError[WAVE_CO2] = "C02 WATER TRAP ERROR";
		} else if ((tempFlagPerSamp & CO2_OCCLUSION_ERR) > 0) {
			// WaveError[WAVE_CO2] = "C02 OCCLUSION ERROR";
			System.out.println("C02 OCCLUSION ERROR");
		} else if ((tempFlagPerSamp & CO2_MODULE_ERR) > 0) {
			System.out.println("C02 MODULE ERROR");
			// WaveError[WAVE_CO2] = "C02 MODULE ERROR";
		} else if ((tempFlagPerSamp & CO2_APNEA) > 0) {
			System.out.println("C02 APNEA ERROR");
			// WaveError[WAVE_CO2] = "C02 APNEA ERROR";
		} else if ((tempFlagPerSamp & CO2_NO_SAMPLE_LINE) > 0) {
			System.out.println("C02  NO SAMPLE LINE");
			// WaveError[WAVE_CO2] = "C02 NO SAMPLE LINE";
		}
		// 33.44,14.379f

		d0 = (outBuf[ModOffset + IN_ETCO2_WAVE1_LSB]);
		d1 = (outBuf[ModOffset + IN_ETCO2_FLAGS] & 0xC0);
		d0 = d0 + d1 * 4;
		d0 = (int) (d0 * CO2GainFactor);
		d0 = -d0;

		// Wave[WAVE_CO2][0] = Wave[WAVE_CO2][2] + (d0 - Wave[WAVE_CO2][2]) / 3;
		// Wave[WAVE_CO2][1] = Wave[WAVE_CO2][0] + (d0 - Wave[WAVE_CO2][2]) / 3;
		// Wave[WAVE_CO2][2] = d0;

		// For Printer Buffer

		// WaveSamplePacket.Co2[0] = d0/1.7974;
		// WaveSamplePacket.Co2[1] = d0>>8; //Most significant bits have the msb bits of
		// wave
		/*
		 * d1 = PrnWavePacket[p3PrnWaveWr].Co2; PrnWavePacket[p2PrnWaveWr].Co2 = d1 +
		 * (d0-d1)/3; PrnWavePacket[p1PrnWaveWr].Co2 = d0 - (d0-d1)/3;
		 * PrnWavePacket[PrnWaveWr].Co2 = d0; //DS @ 30-May-16 //PrnWavePacket[0].Co2 =
		 * d0/1.7974;
		 * 
		 * // Load Pleth data to Wave structure;
		 * 
		 * WPacket[WPwrPtr][W_ETCO2] = Arr[IN_ETCO2_WAVE1_LSB];
		 * WPacket[WPwrPtr][W_ETCO2_FLAGS] = Arr[IN_ETCO2_FLAGS];
		 */

	}

	private void TEMP_Module(int ModOffset, byte[] outBuf) {
		int d0, d1;
		long temp1, temp2;

		if (FrameNum < TEMP_LAST_BYTE) {
			TEMP_DataIn[FrameNum] = outBuf[ModOffset + IN_TEMP_PARA];
		} else if (FrameNum == TEMP_LAST_BYTE) {
			d0 = TEMP_DataIn[TEMP_T1_LSB];
			d1 = TEMP_DataIn[TEMP_T1_MSB];

			temp1 = d0 + (d1 << 8);
			if (temp1 == TEMP_INVALID_VAL) {
				// paraCentre[TEMP_T1_VAL] = "---";
				// CURRENT_ALARMS[TEMP_T1_ALARM] = ALARM_OFF;
			} else {

				System.out.print("Temp " + temp1);

			}

			d0 = TEMP_DataIn[TEMP_T2_LSB];
			d1 = TEMP_DataIn[TEMP_T2_MSB];
			temp2 = d0 + (d1 << 8);
			if (temp2 == TEMP_INVALID_VAL) {
				// paraCentre[TEMP_T1_VAL] = "---";
			} else {

				System.out.print("Temp2 " + temp2);

			}
		}
	}

	private void NIBP_Module(int ModOffset, byte[] outBuf) {

		int NiBpBeep = 0, NiBPInFlags = 0, NibpRxParaCnt = 0;
		int tempFlag = 0, nibpHr = 0;
		String nibpVal = "", nibpMap = "";
		int nibpSis = 0, nibpDia = 0, tempMap = 0;

		int CuffPressure = ((outBuf[ModOffset + IN_CUFF_PRESSURE_LSB])
				| ((outBuf[ModOffset + IN_CUFF_PRESSURE_MSB] & 0x01) << 8));
		if (FrameNum < NIBP_LAST_BYTE) {
			NIBP_DataIn[FrameNum] = outBuf[ModOffset + IN_NIBP_PARA];
		} else if (FrameNum == NIBP_LAST_BYTE) // Draw Parameters at appropriate location;
		{
			tempFlag = NIBP_DataIn[NIBP_RX_FLAGS];
			tempMap = NIBP_DataIn[MAP_NIBP];
			nibpSis = NIBP_DataIn[NIBP_SYSTOLE];
			nibpDia = NIBP_DataIn[NIBP_DIASTOLE];
			nibpHr = NIBP_DataIn[NIBP_HR];

			if (hrSource == HR_NIBP) {
				if (nibpHr == HR_INVALID_VAL) {
					//NSB System.out.println("Nibp HR ---");
				} else {
					//NSB System.out.println("Nibp HR ---" + nibpHr);

				}
			}

			if ((nibpSis) == NIBP_INVALID_VAL || (nibpDia) == NIBP_INVALID_VAL) {
				//NSB nibpVal = "---" + " / " + "---";
			} else {
				nibpVal = nibpSis + " / " + nibpDia;

			}

			if ((tempFlag & BP_IN_PROCESS) > 0) {
				//NSB System.out.println("Nibp Val ---/---");

			}

			if ((tempFlag & BP_COMPLETE) > 0) {

				isNibpProcessing = false;
				System.out.println("Nibp Map " + tempMap);
				System.out.println("Nibp SYS ---" + nibpSis);
				System.out.println("Nibp Dia ---" + nibpDia);

				if (tempMap == NIBP_INVALID_VAL) {
					nibpMap = "---";
				} else {
					nibpMap = tempMap + "";

				}
				//NSB System.out.println("Nibp Map ---" + tempMap);
				//NSB System.out.println("Nibp Val ---/---");
				//NSB System.out.println("Nibp BP HR " + nibpHr);

			}

			nibpMap = tempMap + "";

			if ((tempFlag & BP_COMPLETE) > 0) {
				nibpMap = NIBP_DataIn[MAP_NIBP] + "";

			} else if ((tempFlag & NBP_TIME_OUT) > 0) {
				System.out.println("Nibp Time out");
				nibpVal = "";
				nibpMap = "";

			} else if ((tempFlag & NBP_MEASURE_ERROR) > 0) {
				System.out.println("Nibp  ---Measure Err");
				nibpVal = "";
				nibpMap = "";

			} else if ((tempFlag & NBP_AIR_LEAK) > 0) {
				System.out.println("Nibp ---Air Leak");
				nibpVal = "";
				nibpMap = "";

			} else if ((tempFlag & NBP_AIR_BLOCK) > 0) {
				System.out.println("Nibp  ---Air Block");
				nibpVal = "";
				nibpMap = "";

			} else if ((tempFlag & NBP_WEAK_PULSE) > 0) {
				System.out.println("Nibp ---Weak Pulse");
				nibpVal = "";
				nibpMap = "";

			} else if (nibpMap.equalsIgnoreCase(NIBP_INVALID_VAL + "")) {
				nibpVal = "--- / ---";
				nibpMap = "---";

			} else {

//            paraCentre[NIBP_MSG] = "";
			}
			//NSB System.out.println("Nibp HR ---");
			//NSB System.out.println("Nibp val ---" + nibpVal);
			//NSB System.out.println("Nibp Map ---" + nibpMap);

			// NCMSParaArr[NCMS_IBP3_MAP_LSB]= (byte) tempMap;

		}
		if (isNibpProcessing) { // For rocobro data-----------
			// tNiBPFlagBits |= NCMS_NiBP_PRESENT;
//        CURRENT_ALARMS[NIBP_MAP_ALARM] = ALARM_OFF;
//        CURRENT_ALARMS[NIBP_VAL_ALARM] = ALARM_OFF;

			System.out.println("Nibp Map Cuff ---" + CuffPressure);
//        paraCentre[NIBP_MAP_VAL] = CuffPressure + "";
//        paraCentre[NIBP_MSG] = "";
			// tNiBPFlagBits |= NCMS_NiBP_IN_PROCESS;
//        NCMSParaArr[NCMS_IBP3_MAP_LSB] = (byte) (CuffPressure & 0xff);
//        paraCentre[NIBP_VAL] = "---/---";
		}
	}


}
