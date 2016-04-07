package com.bixi.util;

public interface UXConstants {
	/** ux tool default width */
	int UXwidth = 1200;

	/** ux tool default height */
	int UXheight = 900;

	/** default icon width */
	int ICON_WIDTH = 120;

	/** default icon height */
	int ICON_HEIGHT = 200;

	/** default image width */
	int IMAGE_WIDTH = 600;

	/** default image height */
	int IMAGE_HEIGHT = 800;

	/** draw frame height */
	int FRAME_HEIGHT = 30;

	/** Power Current Scale */
	int CURRENT_SCALE = 7;
	
	/** Power Voltage Scale */
	int VOLTAGE_SCALE = 7;

	/** Align timeline Frame number */
	int ALIGN_FRAME = 20;

	/* IO READ SCALE */
	int IOREAD_SCALE = 100;
	
	/** IO WRITE SCALE */
	int IOWRITE_SCALE = 100;
	
	/** NET RECIVER SCALE */
	int IONETREC_SCALE = 1024;
	
	/** NET TRANSIT SCALE */
	int IONETTRAN_SCALE = 1024;
	
	int LIFECYCLE = 5000*1000;//us
	
	//int CAMERABMPDURATION = 2500;//us
	int CAMERABMPDURATION = 1000;//us
	/**Log from power data excel, 1ms*/
	int POWERDURATION = 1000;//us

	/*Display 8 BMPs, first picture pixcel offset*/
	int FIRSTPICPOS = 75;//pixels
	int PICDELTA = 200;//pixels
	int PICDURATION = 1000;//2.5ms=2500us

	int MULTIPLE = 1000*1000;//us
	
	/**
	 * shell scripe to convert MOV to yuv then to bmp and identify the frame
	 * numbers
	 */
	String SHELLFILE = "yuv2bmp/run.bat";
	
	/** MOV file */
//	String MOVFILE = "DSC_0365";
	
	String CAMERALOGFILE = "/result.txt" ;
	
//	String BMPDIR = "/output/out_scale_rotate/";
	String TMPLOGFILE = "/tmp.log";
	String LOGFILE = "/output/analysis.txt";
	String TRACEFILE = "/mytrace.txt";

    String OUTPUTDIR = "/output/";
	int STALLDELTA = 30*1000;	
	String OUTDIR = "/output/out";	
	String LOGREPORT = "/logReport.xls";
	String POWERREPORT = "/output/datafile.csv";
	
//	int RULERWIDTH = 5000;
	
	int TOTALIMAGES = 5000;
	/*animation replay speed*/
	int ANIMSPEED = 500;
}
