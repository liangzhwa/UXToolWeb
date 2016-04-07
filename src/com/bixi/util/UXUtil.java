package com.bixi.util;

import com.bixi.model.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class UXUtil {
	public static int totalImage = 0;
	// directory name of bmps
	private static String bmppath = "res";
	private static String dest = "out.bmp";
	private static final String DEST_FILE = "dest.bmp";
	private static boolean DEBUG = false;
	/* frame list */
	private static ArrayList<Integer> frameList = new ArrayList<Integer>();
	/* Iothroughput1 list */
	private static ArrayList<Integer> Iothroughput1 = new ArrayList<Integer>();
	/* Iothroughput2 list */
	private static ArrayList<Integer> Iothroughput2 = new ArrayList<Integer>();
	/* Iothroughput3 list */
	private static ArrayList<Integer> Iothroughput3 = new ArrayList<Integer>();
	/* Iothroughput4 list */
	private static ArrayList<Integer> Iothroughput4 = new ArrayList<Integer>();

	/* result from logReport */
	private static Result result;

	/* Iothroughput1 map with Log time stamp */
	private static HashMap<Integer, Integer> Iothroughput1MapTimestamp = new HashMap<Integer, Integer>();
	/* Iothroughput2 map with Log time stamp */
	private static HashMap<Integer, Integer> Iothroughput2MapTimestamp = new HashMap<Integer, Integer>();
	/* Iothroughput3 map with Log time stamp */
	private static HashMap<Integer, Integer> Iothroughput3MapTimestamp = new HashMap<Integer, Integer>();
	/* Iothroughput4 map with Log time stamp */
	private static HashMap<Integer, Integer> Iothroughput4MapTimestamp = new HashMap<Integer, Integer>();

	/* CPU & GPU workload */
	private static cpuidle cpuState = new cpuidle();

	/* frame time stamp delta list */
	private static ArrayList<Integer> deltaList = new ArrayList<Integer>();
	/* camera time stamp delta list */
	private static ArrayList<Integer> cameraDelta = new ArrayList<Integer>();
	/* frame map with each picture name */
	private static HashMap<Integer, String> picFrames = new HashMap<Integer, String>();
	/* frame map with each compositeStart and compositeStop delta */
	public static HashMap<Integer, Integer> frameDuration = new HashMap<Integer, Integer>();
	/* frame number map with Log time stamp */
	private static HashMap<Integer, Double> framenumMapTimestamp = new HashMap<Integer, Double>();
	/* frame list */
	private static ArrayList<Double> noVideoframeList = new ArrayList<Double>();

	/* acquire buffer start time list */
	private static ArrayList<Integer> acquireStartTime = new ArrayList<Integer>();
	/* acquire buffer map with each acquireStart and acquireStop delta */
	public static HashMap<Integer, Integer> acquireDuration = new HashMap<Integer, Integer>();
	/* acquire buffe map with consumer name */
	private static HashMap<Integer, String> acquireMapcomsumer = new HashMap<Integer, String>();

	/* animation list */
	private static List<Animation> animationMap = new ArrayList<Animation>();

	/* InputReader list */
	private static List<Integer> inputReaderList = new ArrayList<Integer>();
	private static List<Integer> readerDelta = new ArrayList<Integer>();

	/* InputDispatcher list */
	private static List<Integer> inputDispatcherList = new ArrayList<Integer>();
	private static List<Integer> dispatcherDelta = new ArrayList<Integer>();

	/* PerformTraversal list threadName obj */
	private static HashMap<String, List<performTraversal>> TraversalMap = new HashMap<String, List<performTraversal>>();

	/* WinAnim list */
	private static List<Integer> winAnimList = new ArrayList<Integer>();
	private static List<Integer> winAnimDelta = new ArrayList<Integer>();

	/* brightness list */

	private static List<Double> brightnessTimeList = new ArrayList<Double>();
	private static List<Integer> brightnessList = new ArrayList<Integer>();
	private static List<Integer> brightnessDelta = new ArrayList<Integer>();

	/* full camera pictures 2000 */
	static LinkedList<String> fullImagesList = new LinkedList<String>();
	/* power log list */
	private static Map<Integer, LogModel> logMap = new HashMap<Integer, LogModel>();
	/* frame map with thread ID and thread name */
	static LinkedList<String> pidList = new LinkedList<String>();
	public static HashMap<String, String> pidMapName = new HashMap<String, String>();
	/* Vsync timeline list */
	private static ArrayList<Integer> VsyncTime = new ArrayList<Integer>();
	private static ArrayList<Long> VsyncDelta = new ArrayList<Long>();
	private static Double startTime = 0.0;

	/* App Draw HashMap threadName List */
	private static HashMap<String, List<discriptionItem>> AppDrawMap = new HashMap<String, List<discriptionItem>>();

	private static int screenStartTime = 0;
	/* store the first picture timestamp */
	private static Double firstPicTime = 0.0;

	private static String traceDir;

	private static class NodeTemp {
		discriptionItem item;
		NodeTemp next;
	}

	static NodeTemp head = null;
	static NodeTemp point = null;
	static NodeTemp newNode = null;
	static int Count = 0;//

	// Insert List
	public static void AddNode(discriptionItem t) {
		newNode = new NodeTemp();
		if (head == null) {
			head = newNode;
		} else {
			point = head;
			while (point.next != null) {
				point = point.next;
			}
			point.next = newNode;
		}
		point = newNode;
		point.item = t;
		point.next = null;
		Count++;
	}

	// Get Value from List
	public static discriptionItem GetValue(int i) {
		if (head == null || i < 0 || i > Count)
			return null;
		int n;
		NodeTemp temp = null;
		point = head;
		for (n = 0; n <= i; n++) {
			temp = point;
			point = point.next;
		}
		return temp.item;
	}

	// Delete List
	public static void DeleteNode(int i) {
		if (i < 0 || i > Count) {
			return;
		}
		if (i == 0) {
			head = head.next;
		} else {
			int n = 0;
			point = head;
			NodeTemp temp = point;
			for (n = 0; n < i; n++) {
				temp = point;
				point = point.next;
			}
			temp.next = point.next;
		}
		Count--;

	}

	/********************************* Export Func ****************************/
	public static void setScreenXStart(int value) {
		screenStartTime = value;
	}

	public static int getScreenXStart() {
		return screenStartTime;
	}

	public static ArrayList<Double> getnoVideoframeList() {
		return noVideoframeList;
	}

	/**
	 * @return delta list between frame i and 1st frame
	 */
	public static ArrayList<Integer> getDelta() {
		return deltaList;
	}

	public int getTotalBmp(String path) {
		return getBmpNames().size();
	}

	public static String getFrameIconName(int frame) {
		return picFrames.get(frame);
	}

	public static Double getFrameTimeStamps(int frame) {
		return framenumMapTimestamp.get(frame);
	}

	public static List<Animation> getAnimationMap() {
		return animationMap;
	}

	public static void setAnimationMap(List<Animation> animationMap) {
		UXUtil.animationMap = animationMap;
	}

	/* Get the acquireBuffer message */

	public static ArrayList<Integer> getAcquireStartTime() {
		return acquireStartTime;
	}

	public static HashMap<Integer, Integer> getAcquireDuration() {
		return acquireDuration;
	}

	public static HashMap<Integer, String> getAcquireMapcomsumer() {
		return acquireMapcomsumer;
	}

	/* inputRead */
	public static List<Integer> getInputReaderList() {
		return inputReaderList;
	}

	public static void setInputReaderList(List<Integer> inputReaderList) {
		UXUtil.inputReaderList = inputReaderList;
	}

	public static List<Integer> getReaderDelta() {
		return readerDelta;
	}

	public static void setReaderDelta(List<Integer> readerDelta) {
		UXUtil.readerDelta = readerDelta;
	}

	/* AppDraw map */
	public static HashMap<String, List<discriptionItem>> getAppDrawMaps() {
		return AppDrawMap;
	}

	/* performTraversal */
	public static HashMap<String, List<performTraversal>> getTraversalMaps() {
		return TraversalMap;
	}

	/* wmAnimation */
	public static List<Integer> getwinAnimList() {
		return winAnimList;
	}

	public static void setwinAnimList(List<Integer> wmAnimList) {
		UXUtil.winAnimList = wmAnimList;
	}

	public static List<Integer> getwinAnimDelta() {
		return winAnimDelta;
	}

	public static void setwinAnimDelta(List<Integer> wmAnimDelta) {
		UXUtil.winAnimDelta = wmAnimDelta;
	}

	/* inputDispatcher */
	public static List<Integer> getInputDispatcherList() {
		return inputDispatcherList;
	}

	public static void setInputDispatcherList(List<Integer> inputDispatcherList) {
		UXUtil.inputDispatcherList = inputDispatcherList;
	}

	public static List<Integer> getDispatcherDelta() {
		return dispatcherDelta;
	}

	public static void setDispatcherDelta(List<Integer> dispatcherDelta) {
		UXUtil.dispatcherDelta = dispatcherDelta;
	}

	public static Double getStartTime() {
		return startTime;
	}

	/* Power data */
	public static Integer getPowerAlignStartTime() {
		return brightnessDelta.get(brightnessDelta.size() - 1);
	}

	public static void setStartTime(Double time) {
		UXUtil.startTime = time;
	}

	public static Map<Integer, LogModel> getLogMap() {
		return logMap;
	}

	/* IO throughput external functions */
	public static List<Integer> getIothroughputDeltaList() {
		return Iothroughput1;
	}

	public static Map<Integer, Integer> getIothroughput1DeltaMap() {
		return Iothroughput1MapTimestamp;
	}

	public static Map<Integer, Integer> getIothroughput2DeltaMap() {
		return Iothroughput2MapTimestamp;
	}

	public static Map<Integer, Integer> getIothroughput3DeltaMap() {
		return Iothroughput3MapTimestamp;
	}

	public static Map<Integer, Integer> getIothroughput4DeltaMap() {
		return Iothroughput4MapTimestamp;
	}

	/* Vsync Info */
	public static ArrayList<Long> getVsyncDeltaList() {
		return VsyncDelta;
	}

	/********************************* Internal Func ****************************/

	public static HashMap<Integer, String> getFrameAndBMPName(int x, int y, int w, int h) {
		List<String> bmps = getBmpNames();
		if (DEBUG)
			System.out.println("frameList.size=" + frameList.size());
		for (Integer s : frameList) {
			if (DEBUG)
				System.out.print(s + ", ");

		}
		HashMap<Integer, String> frameAndBMPs = new HashMap<Integer, String>();
		for (int i = 0; i < bmps.size(); i++) {
			int frame = getFrame(bmps.get(i), x, y, w, h);
			if (frameAndBMPs.containsKey(frame)) {
				continue;
			}
			frameAndBMPs.put(frame, bmps.get(i));
			picFrames.put(frame, bmps.get(i));
		}
		if (DEBUG)
			System.out.println("key:" + frameAndBMPs);
		for (int j = 0; j < frameList.size(); j++) {
			if (DEBUG)
				System.out.println("~~~~~~~frame~~~~~~" + frameAndBMPs.get(frameList.get(j)));
		}
		return frameAndBMPs;
	}

	public static List<Integer> getFrames() {
		return frameList;
	}

	public static HashMap<Integer, Integer> getFrameDuration() {
		return frameDuration;
	}

	/**
	 * get all bmps which name start with scale
	 * 
	 * @return
	 */
	public static List<String> getBmpNames() {
		File f = null;
		ArrayList<String> bmpNames = new ArrayList<String>();
		try {
			f = new File(bmppath);
			File[] bmps = f.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if (name.endsWith(".bmp")) {
						return true;
					}
					return false;
				}
			});
			for (int i = 0; i < bmps.length; i++) {
				StringBuilder src = new StringBuilder(bmppath);
				src.append("/").append(bmps[i].getName());
				bmpNames.add(src.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
		Collections.sort(bmpNames, comparator);
		return bmpNames;
	}

	/**
	 * @return frameID mapping with BMP names
	 */
	public static HashMap<Integer, String> getFrameNames() {
		if (frameList.size() < 1) {
			throw new RuntimeException("frameList must be init before getFrameNames()");
		}
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		try {
			List<String> bmps = getBmpNames();
			for (int i = 0; i < bmps.size(); i++) {
				map.put(frameList.get(i), bmps.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static ImageIcon getIcon(String name) {
		Image image = null;
		ImageIcon icon = null;
		try {
			image = ImageIO.read(new File(name));
			icon = new ImageIcon(image);
			icon.setImage(icon.getImage().getScaledInstance(UXConstants.ICON_WIDTH, UXConstants.ICON_HEIGHT, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icon;
	}

	public static int getFrame(String src, String dest) {
		// for test x and y are the left downt corner point's
		// return getFrame(src, dest, 448, 64, 96, 34);
		return getFrame(src, dest, 450, 66, 94, 31);
	}

	public static int getFrame(String src, int left, int top, int width, int height) {
		return getFrame(src, dest, left, top, width, height);
	}

	/**
	 * @param src
	 *            source image
	 * @param dest
	 *            destination image
	 * @param left
	 *            x
	 * @param top
	 *            top dimension between right down corner
	 * @param width
	 * @param height
	 * @return frame number
	 */
	public static native int getFrame(String src, String dest, int left, int top, int width, int height);

	public static native void sayHello();

	public static HashMap<Integer, String> getCameraLogs(String logfile) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		String[] iconNameLast = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(logfile));
			String[] s = null;
			String line = "";
			String iconName = "";
			Integer iconFrame = null;
			Integer cameraTime = null;
			String strCameraTime = "";
			cameraDelta.clear();
			while ((line = br.readLine()) != null) {
				s = line.split(" ");
				iconName = s[0];
				iconFrame = new Integer(s[1]);
				iconNameLast = iconName.split("_");
				strCameraTime = iconNameLast[iconNameLast.length - 1];
				cameraTime = new Integer(strCameraTime.substring(0, strCameraTime.indexOf("."))) * UXConstants.CAMERABMPDURATION;
				if (!map.containsKey(iconFrame)) {
					map.put(iconFrame, iconName);
					cameraDelta.add(cameraTime);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		picFrames = map;
		return map;
	}

	/**
	 * cut a rectangle of the image file
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param src
	 */
	public static void cutImage(int x, int y, int width, int height, String src) {
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("bmp");
		ImageReader reader = readers.next();
		InputStream source = null;
		ImageInputStream iis = null;
		try {
			source = new FileInputStream(src);
			iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "bmp", new File(DEST_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Result getResult() {
		return result;
	}

	/**
     * 
     */
	public static List<Power> getPowerLog(String csvFilePath) {
		List<Power> powerList = new ArrayList<Power>();
		double tmpTime = 0.0;
		double tmpCurrent = 0.0;
		double tmpPower = 0.0;
		double tmpVoltage = 0.0;
		try {
			ArrayList<String[]> csvList = new ArrayList<String[]>();
			CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
			reader.readHeaders();
			while (reader.readRecord()) {
				csvList.add(reader.getValues());
			}
			reader.close();

			String cell = "";
			for (int row = 0; row < csvList.size(); row++) {
				cell = csvList.get(row)[0];
				if (row != 0) {
					tmpTime = Double.parseDouble(csvList.get(row)[0]);
					tmpCurrent = Double.parseDouble(csvList.get(row)[1]);
					tmpPower = Double.parseDouble(csvList.get(row)[2]);
					tmpVoltage = Double.parseDouble(csvList.get(row)[3]);
					Power power = new Power();
					power.setTime(tmpTime);
					// power.setTime(new Integer(cell.substring(0,
					// cell.indexOf("."))));
					power.setAvgCurrent(tmpCurrent);
					power.setAvgPower(tmpPower);
					power.setAvgVoltage(tmpVoltage);

					powerList.add(power);
					tmpCurrent = 0.0;
					tmpPower = 0.0;
					tmpVoltage = 0.0;
				} else {
					System.out.println(csvList.get(row)[0]);
				}

			}
		} catch (Exception ex) {
			if (DEBUG)
				System.out.println(ex);
		}
		return powerList;
	}

	public static void parseLogFrames2Timestamp(String filename, String tag, boolean hasvideo) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			String log = "";
			Double timestamp = null;
			Double ComStartTimestamp = null;
			int framenum = 0;
			Pattern p = Pattern.compile("[' ']+");
			frameList.clear();
			noVideoframeList.clear();
			brightnessTimeList.clear();
			while ((line = br.readLine()) != null) {
				log = p.matcher(line.trim()).replaceAll(" ");
				String[] s = log.split(" ");
				/*
				 * Fix me: currently method still cannot deal with some strange
				 * log, there's blank space in the first column
				 */
				if ((s[2] != null) && (s[2].indexOf(":") > 2)) {
					timestamp = new Double(s[2].substring(0, s[2].indexOf(":")));
				} else {
					continue;
				}
				if (log.contains(tag)) {
					if (hasvideo) {
						framenum = new Integer(s[4].substring(s[4].lastIndexOf("|") + 1));
						frameList.add(framenum);
						// add frame to frameList and put timestamp into map
						framenumMapTimestamp.put(framenum, ComStartTimestamp);
					} else {
						noVideoframeList.add(ComStartTimestamp);
						// System.out.println("noVideo StartTime:" + timestamp);
					}
				}
				if (log.contains("onMessageReceived")) {
					ComStartTimestamp = timestamp;
				}
				if (log.contains("UXSetBrightness")) {
					brightnessTimeList.add(timestamp);
					System.out.println("brightnessTimeList:" + timestamp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// nothing to do?
		}
	}

	public static void LogFilter(String filename, StringBuffer Dir) {
		LinkedList<BufferedWriter> bwlist = new LinkedList<BufferedWriter>();
		int index = 0;
		String linetmp = "";
		BufferedWriter tmpbw = null;
		BufferedWriter bw = null;
		StringBuffer tmpdir = new StringBuffer(Dir);
		FileWriter filewriter;
		try {
			filewriter = new FileWriter((tmpdir.append("/tmp.log").toString()));
			bw = new BufferedWriter(filewriter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		Pattern p = Pattern.compile("[' ']+");
		try {
			while ((line = br.readLine()) != null) {
				if (line.indexOf(": 0:") > 0) {
					bw.write(line);
					bw.write("\n");
				}
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void PickupAllPIDName(String origFilename) {
		LinkedList<BufferedWriter> bwlist = new LinkedList<BufferedWriter>();
		String line = "";
		BufferedReader br = null;
		pidList.clear();
		pidMapName.clear();
		try {
			br = new BufferedReader(new FileReader(origFilename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			boolean isSame = false;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("next_comm") > 0) {
					String temchar2 = "";
					String pidName = "";
					String pid = "";
					temchar2 = line.substring(line.indexOf("next_comm"), line.indexOf("next_prio") - 1);
					pidName = temchar2.substring(temchar2.indexOf("next_comm") + 10, temchar2.indexOf(" "));
					pid = temchar2.substring(temchar2.indexOf("next_pid") + 9);
					for (int i = 0; i < pidList.size(); i++) {
						if (pidList.get(i).equals(pid)) {// is duplicated
							isSame = true;
							/*
							 * Tempt fix some cases: one application will change
							 * the PID number in one test period
							 */
							pidMapName.put(pid, pidName);
							break;
						}
						isSame = false;
					}
					if (!isSame) {
						pidList.add(pid);
						pidMapName.put(pid, pidName);
					}
				}

			}
			if (DEBUG) {
				for (int i = 0; i < pidList.size(); i++) {
					System.out.println(pidList.get(i) + " " + pidMapName.get(pidList.get(i)));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return pidMapName;
	}

	public static LinkedList<String> LogDealPrepare(String filename, StringBuffer Dir) {
		LinkedList<String> threadNameList = new LinkedList<String>();
		LinkedList<String> threadFiltNameList = new LinkedList<String>();
		LinkedList<BufferedWriter> bwlist = new LinkedList<BufferedWriter>();
		int index = 0;
		String linetmp = "";
		BufferedWriter tmpbw = null;
		boolean isold = false;
		StringBuffer tmpDir = new StringBuffer(Dir);
		AppDrawMap.clear();
		try {
			File file = new File(tmpDir.append("/output/tmp").toString());
			file.mkdirs();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			String log = "";
			String threadName = "";
			char character;
			LogModel logModel = null;
			boolean bOnMessageRec = false;
			Double timestamp = null;
			int i = 0;
			String function = "";
			Pattern p = Pattern.compile("[' ']+");
			while ((line = br.readLine()) != null) {
				log = p.matcher(line.trim()).replaceAll(" ");
				String[] s = log.split(" ");
				threadName = s[0];
				for (int i1 = 0; i1 < threadNameList.size(); i1++) {
					isold = (boolean) (threadName.equals(threadNameList.get(i1)));
					if (isold) {
						bwlist.get(i1).write("\r\n");
						bwlist.get(i1).write(line);
						break;
					}
				}
				if (!isold) {
					StringBuffer tmpdir = new StringBuffer(Dir);
					FileWriter filewriter = new FileWriter((tmpdir.append("/output/tmp/").append(index)).toString());
					if (filewriter != null) {
						BufferedWriter bw = new BufferedWriter(filewriter);
						bw.write("abcd\r\n");
						bw.write(line);
						bwlist.add(bw);
						index++;
						threadNameList.add(threadName);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			for (int i2 = 0; i2 < threadNameList.size() - 1; i2++) {
				// System.out.println(threadNameList.get(i2));
			}
		}
		for (int i5 = 0; i5 < bwlist.size(); i5++) {
			try {
				bwlist.get(i5).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		StringBuffer finaldir = new StringBuffer(Dir);
		try {
			tmpbw = new BufferedWriter(new FileWriter((finaldir.append(UXConstants.LOGFILE)).toString()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (int i5 = 0; i5 < threadNameList.size(); i5++) {
			/*
			 * NodeTemp head = null; NodeTemp point = null; NodeTemp newNode =
			 * null; Count = 0;//ͳ��ֵ
			 */
			int initValue = Count;
			try {
				StringBuffer finaldir2 = new StringBuffer(Dir);
				BufferedReader brtmp = new BufferedReader(new FileReader((finaldir2.append("/output/tmp/").append(i5).toString())));
				while ((linetmp = brtmp.readLine()) != null) {
					if (linetmp.indexOf("abcd") == 0)
						continue;
					/* Start parse the whole logs include all threadName */
					String SubTimeStamp = linetmp.substring(linetmp.indexOf("[00"), linetmp.indexOf(": 0: "));
					Pattern p = Pattern.compile("[' ']+");
					String log = p.matcher(SubTimeStamp.trim()).replaceAll(" ");
					String[] c = log.split(" ");
					Double timestamp = new Double(c[1]);
					// System.out.println(c[1]);
					SubTimeStamp = linetmp.substring(linetmp.indexOf(": 0: ") + 5);
					// System.out.println(timestamp + " " +SubTimeStamp);
					if ((SubTimeStamp.substring(0, 1)).equals("B")) {
						discriptionItem tmpItem = new discriptionItem();
						tmpItem.setStartTime((long) (timestamp * UXConstants.MULTIPLE));
						String SubTag0 = SubTimeStamp.substring(SubTimeStamp.indexOf("|") + 1);
						tmpItem.setsubTag(SubTag0.substring(SubTag0.indexOf("|") + 1));
						String SubTag1 = linetmp.substring(linetmp.indexOf("-"));
						SubTag1 = SubTag1.substring(SubTag1.indexOf("-") + 1, SubTag1.indexOf(" "));
						if (SubTag1.indexOf("-") > 0) {
							SubTag1 = SubTag1.substring(SubTag1.indexOf("-") + 1);
						}
						// else
						// SubTag1 = SubTag1.substring(0, SubTag1.indexOf(" "));
						tmpItem.setpidName(pidMapName.get(SubTag1));
						// System.out.println(tmpItem.getStartTime());
						AddNode(tmpItem);
					} else if ((SubTimeStamp.substring(0, 1)).equals("E")) {
						discriptionItem Item2 = GetValue(Count - 1);
						if (Item2 == null)
							continue;
						Item2.setDelta((int) ((timestamp * UXConstants.MULTIPLE) - Item2.getStartTime()));
						Item2.setLevel(Count - initValue);
						/* Filter unneccessary informations */
						if (Item2.getpidName() != null) {
							if (((Item2.getpidName()).indexOf("Binder") == -1) && ((Item2.getpidName()).indexOf("SurfaceFlinger") == -1)) {
								if (Item2.getStartTime() < (UXUtil.getStartTime() + 5) * (UXConstants.MULTIPLE)) {
									if (AppDrawMap.containsKey(Item2.getpidName())) {
										AppDrawMap.get(Item2.getpidName()).add(Item2);
									} else {
										List<discriptionItem> discList = new ArrayList<discriptionItem>();
										threadFiltNameList.add(Item2.getpidName());
										AppDrawMap.put(Item2.getpidName(), discList);
										AppDrawMap.get(Item2.getpidName()).add(Item2);
									}
									// System.out.println("processname: "
									// +Item2.getpidName()+ "function: "
									// +Item2.getsubTag()+ " start time: " +
									// Item2.getStartTime() + " end time: " +
									// timestamp);
								}
							}
						}

						DeleteNode(Count - 1);
					}
					/* End parse */
					tmpbw.write(linetmp);
					tmpbw.write("\n");
				}
				brtmp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int n = 0; n < threadFiltNameList.size(); n++) {
			List<discriptionItem> discList = AppDrawMap.get(threadFiltNameList.get(n));
			for (int m = 0; m < discList.size(); m++) {
				// System.out.println(discList.get(m).getpidName() + " " +
				// discList.get(m).getsubTag() + " " +
				// discList.get(m).getStartTime() + " " +
				// discList.get(m).getDelta() + " " +
				// discList.get(m).getLevel());
			}
		}
		try {
			tmpbw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return threadNameList;
	}

	public static void getCompositionTime(String filename, boolean hasVideo) {
		Integer frame = 0;
		Integer IothroughputValue1 = null;
		Integer IothroughputValue2 = null;
		Integer IothroughputValue3 = null;
		Integer IothroughputValue4 = null;
		Double compositeStart = null;
		Double compositeStop = null;
		Double acquireStart = null;
		int acquireStartDelta = 0;
		Double acquireStop = null;
		performTraversal traversalitem = null;
		inputReaderList.clear();
		readerDelta.clear();
		inputDispatcherList.clear();
		dispatcherDelta.clear();
		VsyncTime.clear();
		VsyncDelta.clear();
		inputReaderList.clear();
		readerDelta.clear();
		brightnessList.clear();
		brightnessDelta.clear();
		Iothroughput1.clear();
		Iothroughput1MapTimestamp.clear();
		Iothroughput2MapTimestamp.clear();
		Iothroughput3MapTimestamp.clear();
		Iothroughput4MapTimestamp.clear();
		winAnimList.clear();
		winAnimDelta.clear();
		acquireStartTime.clear();
		animationMap.clear();
		TraversalMap.clear();
		acquireMapcomsumer.clear();
		acquireDuration.clear();
		logMap.clear();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			String log = "";
			String inputReaderPid = "";
			String inputDispatcherPid = "";
			char character = 0;
			LogModel logModel = null;
			boolean bOnMessageRec = false;
			boolean bOnAcquire = false;
			boolean bOnPerformTraversal = false;
			boolean bOnEglSwapBuffer = false;
			Double timestamp = null;
			int iCom = 0;
			int iAcq = 0;
			/* count for calculat the performtraversal excute time */
			int iTra = 0;
			/*dispatcherDeltalogMap
			 * count for calculat the performtraversal eglSwapBuffers excute
			 * time
			 */
			int iEglSwap = 0;
			int FrameDelta = 0;
			int iothroughputDelta = 0;
			String function = "";
			Pattern p = Pattern.compile("[' ']+");
			/* pick up InputReader, InputDispatcher pid numbers */
			for (int i = 0; i < pidList.size(); i++) {
				if (pidMapName.get(pidList.get(i)).equals("InputReader")) {
					inputReaderPid = pidList.get(i);
				} else if (pidMapName.get(pidList.get(i)).equals("InputDispatcher")) {
					inputDispatcherPid = pidList.get(i);
				}
			}
			/* Fix me, any common log parse method to replace this?? */
			while ((line = br.readLine()) != null) {
				/* VSync special message format */
				if (line.contains("VSYNC")) {
					String s0 = line.substring(line.indexOf("["));
					log = p.matcher(s0.trim()).replaceAll(" ");
					String[] s1 = log.split(" ");
					timestamp = new Double(s1[1].substring(0, s1[1].indexOf(":")));
					if (timestamp != null) {
						VsyncTime.add((int) (timestamp * UXConstants.MULTIPLE));
						VsyncDelta.add((long) ((timestamp - startTime) * UXConstants.MULTIPLE));
					}
					continue;
				}
				log = p.matcher(line.trim()).replaceAll(" ");
				String[] s = log.split(" ");
				/*
				 * Fix me: currently method still cannot deal with some strange
				 * log, there's blank space in the first column
				 */
				if (s[2].indexOf(":") > 2) {
					timestamp = new Double(s[2].substring(0, s[2].indexOf(":")));
				} else {
					continue;
				}
				if (s[4] != null)
					character = s[4].charAt(0);
				if (s[0].contains(inputReaderPid)) {
					inputReaderList.add((int) (timestamp * UXConstants.MULTIPLE));
					readerDelta.add((int) Math.round((timestamp - startTime) * UXConstants.MULTIPLE));
				}
				if (s[0].contains(inputDispatcherPid) && (line.indexOf("iq") > 0)) {
					inputDispatcherList.add((int) (timestamp * UXConstants.MULTIPLE));
					dispatcherDelta.add((int) Math.round((timestamp - startTime) * UXConstants.MULTIPLE));
				}
				if (log.contains("SetBrightness")) {
					brightnessList.add((int) (timestamp * UXConstants.MULTIPLE));
					brightnessDelta.add((int) Math.round((timestamp - startTime) * UXConstants.MULTIPLE));
				}
				if (log.contains("IONetStats")) {
					Pattern p1 = Pattern.compile("[|]+");
					String str = p1.matcher(s[4]).replaceAll(" ");
					IothroughputValue1 = new Integer(str.split(" ")[3]);
					IothroughputValue2 = new Integer(str.split(" ")[4]);
					IothroughputValue3 = new Integer(str.split(" ")[5]);
					IothroughputValue4 = new Integer(str.split(" ")[6]);
					// add frame to frameList and put timestamp into map
					iothroughputDelta = (int) Math.round((timestamp - startTime) * UXConstants.MULTIPLE);
					Iothroughput1.add(iothroughputDelta);
					Iothroughput1MapTimestamp.put(iothroughputDelta, IothroughputValue1);
					Iothroughput2MapTimestamp.put(iothroughputDelta, IothroughputValue2);
					Iothroughput3MapTimestamp.put(iothroughputDelta, IothroughputValue3);
					Iothroughput4MapTimestamp.put(iothroughputDelta, IothroughputValue4);
				}
				if (log.contains("performTraversals")) {
					bOnPerformTraversal = true;
					traversalitem = extractPerformTraversals(log);
				}
				if (log.contains("wmAnimate")) {
					winAnimList.add((int) (timestamp * UXConstants.MULTIPLE));
					winAnimDelta.add((int) Math.round((timestamp - startTime) * UXConstants.MULTIPLE));
				}
				if ((log.contains("compositionDbg")) && (hasVideo)) {
					frame = new Integer(s[4].substring(s[4].lastIndexOf("|") + 1));
					function = s[4];
				} else if (log.contains("onMessageReceived")) {
					bOnMessageRec = true;
					compositeStart = timestamp; // float format
				} else if (log.contains("acquireBuffer")) {
					bOnAcquire = true;
					acquireStart = timestamp;
					acquireStartDelta = (int) ((acquireStart - startTime) * UXConstants.MULTIPLE);
					acquireStartTime.add(acquireStartDelta);
				} else if (log.contains("Anim") && !log.contains("wmAnimate")) {
					Animation anim = extractAnimation(log);
					String animName = anim.getName();
					boolean hasFound = false;
					if (animName != null) {
						for (int m = 0; m < animationMap.size(); m++) {
							if ((animationMap.get(m).getName() != null) && ((animationMap.get(m).getName()).equals(animName)) && (animationMap.get(m).getEndTime() < 10)) {
								final Double end = anim.getEndTime();
								if (end > 0.0) {
									animationMap.get(m).setEndTime(end);
								}
								hasFound = true;
								break;
							}
						}
						if ((!hasFound) && (anim.getStartTime() > 10)) {
							animationMap.add(anim);
						}
					}
				}
				if (bOnPerformTraversal) {
					if (character == 'B') {
						if (log.contains("eglSwapBuffers")) {
							traversalitem.setEglSwapTimeB((long) (timestamp * (double) (UXConstants.MULTIPLE)));
							bOnEglSwapBuffer = true;
						}
						if (bOnEglSwapBuffer) {
							iEglSwap++;
						}
						iTra++;
					} else if (character == 'E') {
						if (bOnEglSwapBuffer) {
							if (--iEglSwap == 0) {
								bOnEglSwapBuffer = false;
								traversalitem.setEglSwapTimeE((long) (timestamp * (double) (UXConstants.MULTIPLE)));
							}
						}
						if (--iTra == 0) {
							bOnPerformTraversal = false;
							traversalitem.setEndTime((long) (timestamp * (double) (UXConstants.MULTIPLE)));
							String pidName = traversalitem.getpidName();
							if (TraversalMap.containsKey(pidName)) {
								TraversalMap.get(pidName).add(traversalitem);
							} else {
								List<performTraversal> traversalList = new ArrayList<performTraversal>();
								TraversalMap.put(pidName, traversalList);
								TraversalMap.get(pidName).add(traversalitem);
							}
							if (DEBUG)
								System.out.println(" end:" + timestamp);
							// System.out.println("traversalitem Start:" +
							// traversalitem.getStartTime()+ " end:" +
							// traversalitem.getEndTime());
						}
					}
				}

				if (bOnAcquire) {
					if (character == 'B') {
						if (iAcq == 1) {
							/*
							 * if has got the acquireBuffer message, then the
							 * next line should contain the consumer's name
							 */
							acquireMapcomsumer.put(acquireStartDelta, s[4]);
						}
						iAcq++;
					} else if (character == 'E') {
						if (--iAcq == 0) {
							bOnAcquire = false;
							acquireStop = timestamp;
							acquireDuration.put(acquireStartDelta, (int) ((acquireStop - acquireStart) * UXConstants.MULTIPLE));
							// if(DEBUG)
							// System.out.println("acquireStart:" +
							// acquireStartDelta + " duration:" + (acquireStop -
							// acquireStart) * UXConstants.MULTIPLE);
						}
					}
				}

				if (bOnMessageRec) {
					if (character == 'B') {
						iCom++;
					} else if (character == 'E') {
						if (--iCom == 0) {
							bOnMessageRec = false;
							compositeStop = timestamp;
							frameDuration.put(frame, (int) ((compositeStop - compositeStart) * UXConstants.MULTIPLE));
							if (DEBUG)
								System.out.println("frameNum:" + frame + " CompositeTime:" + (compositeStop - compositeStart) * UXConstants.MULTIPLE);
							if (logMap.containsKey(frame)) {
								logModel = logMap.get(frame);
							} else {
								logModel = new LogModel();
								logMap.put(frame, logModel);
								logModel.setFrameNumber(frame);
							}
							if (!hasVideo)
								frame++;
							int pid = new Integer(s[0].substring(s[0].indexOf("-") + 1, s[0].length()));
							// logModel.setPid(pid);
							// logModel.setFunction(function);
							// logModel.setTimestamp(framenumMapTimestamp.get(frame));
							// logModel.setLogTimeDelta(deltaList);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			frame = 0;
		}
	}

	public static void getCpuIdleTime(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = "";
			cpuState.setStartTime(startTime);
			while ((line = br.readLine()) != null) {
				if (line.contains("cpu_idle:") || line.contains("power_start:") || line.contains("power_end:") || line.contains("gpu_state:"))
					cpuState.processidle(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void preAction(String filename) {
		System.out.println("======method preaction start==========");
		try {
			int fragNum = 0;
			BufferedReader br_temp = new BufferedReader(new FileReader(filename));
			String line_temp = "";
			Pattern p = Pattern.compile("[' ']+");
			while ((line_temp = br_temp.readLine()) != null && !line_temp.contains("cpu_idle:"))
				;
			if (line_temp != null) {
				String[] s = p.matcher(line_temp.trim()).replaceAll(" ").split(" ");
				fragNum = s.length;
				for (String x : s) {
					System.out.println(x);
				}
			}

			if (fragNum == 7) {
				File targetFile = new File(filename);
				File distFile = new File(filename.replaceAll("mytrace.txt", "my-t-r-a-c-e_orign.txt"));
				copyFile(targetFile, distFile);
				br_temp.close();
				BufferedReader br2 = new BufferedReader(new FileReader(distFile));
				BufferedWriter wr = new BufferedWriter(new FileWriter(targetFile));
				while ((line_temp = br2.readLine()) != null) {
					String aline = line_temp.replaceAll("tracing_mark_write", "0");
					String newLine = aline + "\n";
					String s[] = p.matcher(aline.trim()).replaceAll(" ").split(" ");
					if (s.length > 3) {
						int cpuIndex = 1;
						while ((cpuIndex < s.length) && !s[cpuIndex].startsWith("[")) {
							cpuIndex++;
						}

						if (cpuIndex < s.length) {
							String temp = " " + s[cpuIndex + 1] + " ";
							newLine = newLine.replaceAll(temp, "  ");
						}
					}
					wr.write(newLine);
				}
				wr.close();
				br2.close();
			}
		} catch (Exception e) {
			System.out.println("======================");
			System.out.println(e.getMessage());
			System.out.println("======================");
			e.printStackTrace();
		}
	}

	public static ArrayList<Integer> getcpuidle_start_ts(int index) {
		return cpuState.getcpuidle_start_ts(index);

	}

	public static ArrayList<Integer> getcpuidle_end_ts(int index) {
		return cpuState.getcpuidle_end_ts(index);

	}

	public static ArrayList<Integer> getcpuidle_states(int index) {
		return cpuState.getcpuidle_states(index);

	}

	private static performTraversal extractPerformTraversals(String line) {
		String threadName = "";
		Double timestamp = null;
		performTraversal traversal = new performTraversal();
		String[] s = line.trim().split(" ");
		for (int i = 0; i < s.length; i++) {
			if (DEBUG)
				System.out.println(s[i]);
		}
		timestamp = new Double(s[2].substring(0, s[2].indexOf(":")));
		threadName = pidMapName.get(s[0].substring(s[0].indexOf("-") + 1));
		if (DEBUG)
			System.out.println(s[0].substring(s[0].indexOf("-") + 1) + " " + threadName);
		traversal.setpidName(threadName);
		traversal.setStartTime((long) (timestamp * (double) (UXConstants.MULTIPLE)));
		traversal.setDelta((int) ((timestamp - startTime) * (double) (UXConstants.MULTIPLE)));
		if (DEBUG)
			System.out.println(threadName + " " + timestamp);
		return traversal;
	}

	private static Animation extractAnimation(String line) {
		String appAnimName = "";
		String windowAnimApp = "";
		Double timestamp = null;
		Double animStartTime = null;
		Double animEndTime = null;
		Animation anim = new Animation();
		String[] s = line.trim().split(" ");
		for (int i = 0; i < s.length; i++) {
			if (DEBUG)
				System.out.println(s[i]);
		}
		try {
			timestamp = new Double(s[2].substring(0, s[2].indexOf(":")));
			if (line.contains("AppWindow")) {
				if (line.contains("AnimStarting")) {
					appAnimName = line.substring(line.indexOf("AnimStarting"));
					appAnimName = appAnimName.substring(appAnimName.indexOf("AnimStarting") + 12, appAnimName.indexOf("|"));
					animStartTime = timestamp;
				} else if (line.contains("AnimEnding")) {
					appAnimName = line.substring(line.indexOf("AnimEnding"));
					appAnimName = appAnimName.substring(appAnimName.indexOf("AnimEnding") + 10, appAnimName.indexOf("|"));
					animEndTime = timestamp;
				}
				anim.setName(appAnimName);
				anim.setType("appAnimation");

			} else if (line.contains("WindowState")) {
				if (line.contains("AnimStarting")) {
					windowAnimApp = line.substring(line.indexOf("AnimStarting"));
					windowAnimApp = windowAnimApp.substring(windowAnimApp.indexOf("AnimStarting") + 12, windowAnimApp.indexOf("|"));
					animStartTime = timestamp;
				} else if (line.contains("AnimEnding")) {
					windowAnimApp = line.substring(line.indexOf("AnimEnding"));
					windowAnimApp = windowAnimApp.substring(windowAnimApp.indexOf("AnimEnding") + 10, windowAnimApp.indexOf("|"));
					animEndTime = timestamp;
				}
				anim.setName(windowAnimApp);
				anim.setType("windowAnimation");
			}
			if (animStartTime != null)
				anim.setStartTime(animStartTime);
			if (animEndTime != null)
				anim.setEndTime(animEndTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return anim;
	}

	public static void ScanFrame(String logFile) {
		LinkedList<String> tmpframeImagesList = new LinkedList<String>();// store the same frame number pictures
		parseLogFrames2Timestamp(logFile, "onMessageReceived", false);
		deltaList.clear();
		System.out.println("=================================start time=========================");
		startTime = brightnessTimeList.get(0);
		System.out.println("noVideo StartTime:" + startTime);
		/* get the log file framenums' timestamp for drawing */
		for (int i = 0; i < noVideoframeList.size(); i++) {
			deltaList.add((int) ((noVideoframeList.get(i) - startTime) * (double) UXConstants.MULTIPLE));
		}
	}

	public static HashMap<Integer, String> filterImagesByFrame(String dir, String CamLog, String logFile) {
		LinkedList<String> tmpframeImagesList = new LinkedList<String>();// store the same frame number pictures
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		parseLogFrames2Timestamp(logFile, "composition", true);
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(CamLog);
		try {
			BufferedReader br = new BufferedReader(new FileReader(CamLog));
			String line = "";
			int alignPoint = 0;
			int frame = 0;
			boolean validstart = false;
			boolean readFirstPic = false;
			int preFrame = 0;
			LogModel log = null;
			double imageTime = 0.0;
			String imageName = " ";
			fullImagesList.clear();
			deltaList.clear();
			System.out.println("=========================camlog=============================");
			while ((line = br.readLine()) != null) {
				imageName = line.split(" ")[0].replace("-", "");
				if (!readFirstPic) {
					firstPicTime = new Double(imageName.substring(0, imageName.indexOf(".")));
					System.out.println("first Pic time= " + firstPicTime);
					readFirstPic = true;
				}
				StringBuffer imageDir = new StringBuffer();
				fullImagesList.add(imageDir.append(dir).append("/output/dst/").append(imageName).toString());
				frame = new Integer(line.split(" ")[1]);
				if ((frame >= 0)) {
					if (DEBUG)
						System.out.println("frame = " + frame + " bmp: " + preFrame);
					/* avoid the preFrame initial value */
					if (preFrame == 0) {
						preFrame = frame;
					}
					if (frame == preFrame) {
						/* just add picture name to tmpframeImagesList arrayList */
						StringBuffer imageDir2 = new StringBuffer();
						tmpframeImagesList.add(imageDir2.append(dir).append("/output/dst/").append(imageName).toString());
					} else if (frame >= preFrame + 1) {
						if (alignPoint != 0) {
							map.put(frame - 1, tmpframeImagesList.get(tmpframeImagesList.size() > 1 ? (tmpframeImagesList.size() / 2) : 0));
						}
						if ((framenumMapTimestamp.get(frame) != null) && (!validstart)) {
							Double cameraTime = null;
							cameraTime = new Double(imageName.substring(0, imageName.indexOf(".")));
							/*deltaList
							 * if the changing frame is both exit in Logfile and
							 * BMP picture, then we get the align point
							 */
							startTime = framenumMapTimestamp.get(frame) - (cameraTime - firstPicTime) * 0.001;
							validstart = true; // needn't to get start time
												// more, but we still need to
												// finish the pics scan							
							// if(DEBUG)
							System.out.println("alignTimeStamp = " + framenumMapTimestamp.get(frame) + " cameraTime: " + cameraTime + " startTime: " + startTime);
						}
						alignPoint++;
						preFrame = frame;
						tmpframeImagesList.clear();
						tmpframeImagesList.add(imageName);// new list coming
					}
					if (logMap.containsKey(frame)) {
						log = logMap.get(frame);
					} else {
						log = new LogModel();
						logMap.put(frame, log);
						log.setFrameNumber(frame);
					}
					String end = imageName.substring(imageName.lastIndexOf("_") + 1, imageName.indexOf("."));
					imageTime = new Double(end) * 1;
					log.setImageName(imageName);
					log.setImageTime(imageTime);
				}
			}
			/* take care the last frame */
			map.put(frame, tmpframeImagesList.get(tmpframeImagesList.size() > 1 ? (tmpframeImagesList.size() / 2) : 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if(DEBUG)
		System.out.println("size = " + map.size());
		List<Integer> list = new ArrayList<Integer>(map.keySet());
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			if (DEBUG) {
				System.out.print(list.get(i) + "--" + map.get(list.get(i)));
				System.out.println();
			}
		}
		/* get the log file framenums' timestamp for drawing */
		for (int i = 0; i < frameList.size(); i++) {
			deltaList.add((int) ((framenumMapTimestamp.get(frameList.get(i)) - startTime) * UXConstants.MULTIPLE));
			if (DEBUG)
				System.out.println("frameTimeStamp: " + framenumMapTimestamp.get(frameList.get(i)) + " startTime: " + startTime);
		}
		picFrames = map;
		return map;
	}

	public static void copyFile(File src, File dest) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(src));
			bos = new BufferedOutputStream(new FileOutputStream(dest));
			byte[] b = new byte[1024 * 4];
			int len;
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean deleteFileDir(String path) {
		File f = null;
		boolean flag = true;
		if (path == null || path.equals(""))
			return false;

		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		try {
			f = new File(path);
			if (!f.exists()) {
				return false;
			}
			if (f.isFile()) {
				return f.delete();
			}
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					flag = files[i].delete();
				} else {
					// delete current sub directory
					flag = deleteFileDir(files[i].getAbsolutePath());
				}
				if (!flag) {
					break;
				}
			}
			// delete current directory
			if (flag && f.delete()) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void printSortedMap(Map<Integer, Double> map) {
		List<Integer> list = new ArrayList<Integer>(map.keySet());
		Collections.sort(list);
		System.out.print("map: [");
		for (int i = 0; i < list.size() - 1; i++) {
			if (DEBUG)
				System.out.print(list.get(i) + "--" + map.get(list.get(i)) + ",");
		}
		if (DEBUG) {
			System.out.print(list.get(list.size() - 1) + "--" + map.get(list.get(list.size() - 1)) + "]");
			System.out.println();
		}
	}

	/********************************* Bat file run command code ****************************/
	public static void setTraceDir(String dirName) {
		traceDir = dirName;
	}

	public static String getTraceDir() {
		return traceDir;
	}

	/**
	 * run a bat file in windows
	 * 
	 * @param batName
	 * @param param
	 */
	public static int runUxTune(String logfile, String startTime, String endTime) {
		int res = 0;
		try {
			String command = null;
			command = new StringBuilder("cmd /c cd dist && uxtune2 ").append("-s ").append(startTime).append(" ").append("-e ").append(endTime).append(" ").append(logfile).toString();
			System.out.println("Uxtune cmd: " + command);
			Process p = Runtime.getRuntime().exec(command);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			final BufferedReader br = new BufferedReader(isr);
			new Thread(new PrintBuffer(br)).start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			new Thread(new PrintBuffer(br2)).start();
			if (p.waitFor() == 0) {
				if (DEBUG)
					System.out.println("success");
			} else {
				if (DEBUG)
					System.out.println("failer");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	public static int runBatCmd(String fileDir, String firstFrame, int flag) {
		int res = 0;
		try {
			String command = null;
			switch (flag) {
			case 1:
				command = new StringBuilder("cmd /c cd UXScript && getFrameNum ").append(fileDir).append(" ").toString();
				break;
			case 2:
				command = new StringBuilder("cmd /c cd UXScript && mov2pic ").append(fileDir).append(" ").append(firstFrame).toString();
				break;
			}
			Process p = Runtime.getRuntime().exec(command);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			final BufferedReader br = new BufferedReader(isr);
			new Thread(new PrintBuffer(br)).start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			new Thread(new PrintBuffer(br2)).start();
			if (p.waitFor() == 0) {
				if (DEBUG)
					System.out.println("success");
			} else {
				if (DEBUG)
					System.out.println("failer");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/********************************* Generate EXCEL report ****************************/
	public static Result getResultFromLogReport(String reportName) {
		Result ret = new Result();
		File fileTemp = new File(reportName);
		List<String[]> tempPhases = new ArrayList<String[]>();
		if(!fileTemp.exists()){
			result = ret; 
			ret.setPhaseList(tempPhases);
			return null;
		}
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(reportName));
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell0 = null;
			String name = null;
			String value = null;
			Double dval = 0.0;
			Integer ival = 0;
			int j = 0;
			int indexStallList = 0;
			int indexLTFList = 0;
			int indexDetailInfo = 0;
			boolean bSpace = false;
			while (true) {
				row = sheet.getRow(j);
				if (row == null)
					break;
				cell0 = row.getCell((short) 0);
				if (cell0 != null) {
					bSpace = false;
					if (cell0 != null && cell0.getCellType() == 1) {
						name = cell0.getStringCellValue();
						if (name.equals("Generate Stall List:"))
							indexStallList = j;
						else if (name.equals("Generate LTF list:"))
							indexLTFList = j;
						else if (name.equals("Render Thread Detail Infomation:"))
							indexDetailInfo = j;
					}
				} else {
					if (bSpace)
						break;
					bSpace = true;
				}
				j++;
			}
			
			for (int i = 0; i < 40; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				HSSFCell cell1 = row.getCell((short) 1);
				if (cell1 != null) {
					if (row.getCell((short) 1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						dval = row.getCell((short) 1).getNumericCellValue();
						ival = new Integer((int) dval.doubleValue());
						value = ival.toString();
					} else if (row.getCell((short) 1).getCellType() == HSSFCell.CELL_TYPE_STRING) {
						value = row.getCell((short) 1).getStringCellValue();
					}
					switch (i) {
					case 1:
						ret.setPowerConsumption(value);
						System.out.println("first columb: " + value);
						break;
					case 2:
						ret.setDiskIO(value);
						break;
					case 3:
						ret.setNnetIO(value);
						break;
					case 4:
						ret.setReactionTime(value);
						break;
					case 5:
						ret.setResponseTime(value);
						break;
					case 6:
						ret.setTotalFPS(value);
						break;
					case 7:
						ret.setGeneralStallNum(value);
						break;
					case 8:
						ret.setTotalStallNum(value);
						break;
					case 9:
						ret.setTotalLTFNum(value);
						break;
					default:
						break;
					}
				} else {
					int tempPhaseNum = (int) sheet.getRow(7).getCell((short) 1).getNumericCellValue();
					for (int k = 1; k <= tempPhaseNum; k++) {
						if (row.getCell((short) 0).getStringCellValue().equals("Phase " + String.valueOf(k))) {
							tempPhases.add(new String[] { sheet.getRow(i + 4).getCell((short) 0).getStringCellValue(), sheet.getRow(i + 7).getCell((short) 0).getStringCellValue() });
						}
					}
				}
			}

			ret.setPhaseList(tempPhases);

			/* get generate stall list */
			List<Stall> generalStallList = new ArrayList<Stall>();
			for (int k = indexStallList + 2; k < indexLTFList - 1; k++) {
				Stall stall = new Stall();
				row = sheet.getRow(k);
				stall.setThreadName(row.getCell((short) 0).getStringCellValue());
				stall.setFrameNum(row.getCell((short) 1).getNumericCellValue());
				stall.setStartTime(row.getCell((short) 2).getStringCellValue());
				stall.setEndTime(row.getCell((short) 3).getStringCellValue());
				stall.setDuration(row.getCell((short) 4).getStringCellValue());
				double delta = new Double(row.getCell((short) 2).getStringCellValue()) - startTime;
				stall.setDelta(delta);
				generalStallList.add(stall);
			}
			ret.setGeneralStallList(generalStallList);

			/* get all LTF list */
			List<LTF> allLTFList = new ArrayList<LTF>();
			for (int m = indexLTFList + 2; m < indexDetailInfo - 1; m++) {
				LTF ltf = new LTF();
				row = sheet.getRow(m);
				ltf.setThreadName(row.getCell((short) 0).getStringCellValue());
				ltf.setFrameNum(row.getCell((short) 1).getNumericCellValue());
				ltf.setStartTime(row.getCell((short) 2).getStringCellValue());
				ltf.setEndTime(row.getCell((short) 3).getStringCellValue());
				ltf.setDuration(row.getCell((short) 4).getStringCellValue());
				ltf.setFrameStartTime(row.getCell((short) 5).getStringCellValue());
				ltf.setFrameEndTime(row.getCell((short) 6).getStringCellValue());
				ltf.setFrameDuration(row.getCell((short) 7).getStringCellValue());
				double delta = new Double(row.getCell((short) 2).getStringCellValue()) - startTime;
				ltf.setDelta(delta);
				allLTFList.add(ltf);
			}
			ret.setAllLTFList(allLTFList);

			/* get render thread detail information */
			Map<String, RenderThreadDetailInfo> threadInfoMap = new HashMap<String, RenderThreadDetailInfo>();
			int n = indexDetailInfo + 2;
			boolean isNumeric = false;
			boolean isLTF = false;
			double frameNum = 0;
			bSpace = true;
			RenderThreadDetailInfo threadInfo = null;
			List<Stall> stallList = null;
			List<LTF> ltfList = null;
			// parse thread detail information to threadInfoMap
			while (true) {
				row = sheet.getRow(n);
				if (row != null) {
					cell0 = row.getCell((short) 0);
					if (cell0 != null) {
						if (bSpace) {
							threadInfo = new RenderThreadDetailInfo();
							stallList = new ArrayList<Stall>();
							ltfList = new ArrayList<LTF>();
						}
						if (cell0.getCellType() == 1) {// cellType 1 is String
							isNumeric = false;
							name = cell0.getStringCellValue();
						} else if (cell0.getCellType() == 0) {// cellType 0 is
																// Numeric
							isNumeric = true;
							frameNum = cell0.getNumericCellValue();
						}
						if (name.contains("-"))
							threadInfo.setThreadName(name.substring(0, name.lastIndexOf("-")));
						else if (name.equals("FPS"))
							threadInfo.setFps(row.getCell((short) 1).getNumericCellValue());
						else if (name.equals("Frame Number") && row.getCell((short) 1).getCellType() == 0)
							threadInfo.setFrameNums((int) row.getCell((short) 1).getNumericCellValue());
						else if (name.equals("LTF Num"))
							threadInfo.setLTFNums((int) row.getCell((short) 1).getNumericCellValue());
						else if (name.equals("Stall Num"))
							threadInfo.setStallNums((int) row.getCell((short) 1).getNumericCellValue());

						if (name.contains("LTF list"))
							isLTF = true;
						if (isNumeric) {
							LTF ltf = new LTF();
							Stall stall = new Stall();
							if (isLTF) {
								ltf.setFrameNum(frameNum);
								ltf.setThreadName(threadInfo.getThreadName());
								ltf.setStartTime(row.getCell((short) 1).getStringCellValue());
								ltf.setEndTime(row.getCell((short) 2).getStringCellValue());
								ltf.setDuration(row.getCell((short) 3).getStringCellValue());
								ltf.setFrameStartTime(row.getCell((short) 4).getStringCellValue());
								ltf.setFrameEndTime(row.getCell((short) 5).getStringCellValue());
								ltf.setFrameDuration(row.getCell((short) 6).getStringCellValue());
								ltf.setDelta(new Double(ltf.getStartTime()) - startTime);
								ltfList.add(ltf);
							} else {
								stall.setFrameNum(frameNum);
								stall.setThreadName(threadInfo.getThreadName());
								stall.setStartTime(row.getCell((short) 1).getStringCellValue());
								stall.setEndTime(row.getCell((short) 2).getStringCellValue());
								stall.setDuration(row.getCell((short) 3).getStringCellValue());
								stall.setFrameStartTime(row.getCell((short) 4).getStringCellValue());
								stall.setFrameEndTime(row.getCell((short) 5).getStringCellValue());
								stall.setFrameDuration(row.getCell((short) 6).getStringCellValue());
								stall.setDelta(new Double(stall.getStartTime()) - startTime);
								stallList.add(stall);
							}
						}
					}
					bSpace = false;
				} else {
					if (bSpace)
						break;
					bSpace = true;
					isLTF = false;
				}
				if (cell0.getCellType() == 1 && cell0.getStringCellValue().isEmpty()) {
					bSpace = true;
					isLTF = false;
				}
				n++;
				if (bSpace) {
					threadInfo.setStallList(stallList);
					threadInfo.setLtfList(ltfList);
					threadInfoMap.put(threadInfo.getThreadName(), threadInfo);

				}
			}
			ret.setThreadInfoMap(threadInfoMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = ret;
		return ret;
	}

	public static void excelReader(String xls) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xls));
			HSSFSheet sheet = workbook.getSheetAt(0);
			int i = 0;
			while (true) {
				HSSFRow row = sheet.getRow(i);
				if (row == null) {
					break;
				}
				HSSFCell cell0 = row.getCell((short) 0);
				HSSFCell cell1 = row.getCell((short) 1);
				HSSFCell cell2 = row.getCell((short) 2);
				if (DEBUG) {
					System.out.print(cell0.getStringCellValue());
					System.out.print("," + cell1.getStringCellValue());
					System.out.print("," + cell2.getStringCellValue());
				}
				i++;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void excelCreater(String powerReport, String logReport) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			// HSSFSheet sheet = workbook.createSheet("sheet0");
			HSSFSheet sheet = workbook.createSheet();
			HSSFRow row = sheet.createRow((short) 0);

			HSSFCell frameNumCell = row.createCell((short) 0);
			frameNumCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			frameNumCell.setCellValue("Frame Number");

			HSSFCell timestampCell = row.createCell((short) 1);
			timestampCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			timestampCell.setCellValue("TimeStamp");

			HSSFCell logTimeDeltaCell = row.createCell((short) 2);
			logTimeDeltaCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			logTimeDeltaCell.setCellValue("LogTimeDelta");

			HSSFCell imageNameCell = row.createCell((short) 3);
			imageNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			imageNameCell.setCellValue("Image Name");

			HSSFCell imageTimeCell = row.createCell((short) 4);
			imageTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			imageTimeCell.setCellValue("ImageTime");

			HSSFCell functionCell = row.createCell((short) 5);
			functionCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			functionCell.setCellValue("Function");

			HSSFCell pidCell = row.createCell((short) 6);
			pidCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			pidCell.setCellValue("PID");

			HSSFCell startTimeCell = row.createCell((short) (short) 7);
			startTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			startTimeCell.setCellValue("StartTime");

			HSSFCell endTimeCell = row.createCell((short) 8);
			endTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			endTimeCell.setCellValue("EndTime");

			LogModel logModel = null;
			for (int i = 1; i <= frameList.size(); i++) {
				logModel = logMap.get(frameList.get(i - 1));
				row = sheet.createRow((short) i);
				frameNumCell = row.createCell((short) 0);
				frameNumCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				frameNumCell.setCellValue(logModel.getFrameNumber());

				timestampCell = row.createCell((short) 1);
				timestampCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				timestampCell.setCellValue(logModel.getTimestamp());

				logTimeDeltaCell = row.createCell((short) 2);
				logTimeDeltaCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// logTimeDeltaCell.setCellValue(logModel.getLogTimeDelta());

				imageNameCell = row.createCell((short) 3);
				imageNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				imageNameCell.setCellValue(logModel.getImageName());

				imageTimeCell = row.createCell((short) 4);
				imageTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				imageTimeCell.setCellValue(logModel.getImageTime());

				functionCell = row.createCell((short) 5);
				functionCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				functionCell.setCellValue(logModel.getFunction());

				pidCell = row.createCell((short) 6);
				pidCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				pidCell.setCellValue(logModel.getPid());

				startTimeCell = row.createCell((short) 7);
				startTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				startTimeCell.setCellValue(logModel.getStartTime());

				endTimeCell = row.createCell((short) 8);
				endTimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				endTimeCell.setCellValue(logModel.getEndTime());
			}

			int totalFrames = frameList.size();
			row = sheet.createRow(totalFrames + 2);
			HSSFCell resultNameCell = row.createCell((short) 0);
			resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultNameCell.setCellValue("Result:");
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			HSSFFont font = workbook.createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.RED.index);
			cellStyle.setFont(font);
			resultNameCell.setCellStyle(cellStyle);

			row = sheet.createRow(totalFrames + 3);
			resultNameCell = row.createCell((short) 0);
			resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultNameCell.setCellValue("Total frames:");
			resultNameCell.setCellStyle(cellStyle);
			HSSFCell resultValueCell = row.createCell((short) 1);
			resultValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultValueCell.setCellValue(totalFrames);
			double totalTime = UXUtil.getFrameTimeStamps(frameList.get(totalFrames - 1)) - UXUtil.getFrameTimeStamps(frameList.get(0));
			Double d = (double) totalFrames / totalTime;
			BigDecimal b = new BigDecimal(d);
			Double fps = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			row = sheet.createRow(totalFrames + 4);
			resultNameCell = row.createCell((short) 0);
			resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultNameCell.setCellValue("FPS:");
			resultNameCell.setCellStyle(cellStyle);
			resultValueCell = row.createCell((short) 1);
			resultValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultValueCell.setCellValue(fps + "(fps)");

			row = sheet.createRow(totalFrames + 5);
			resultNameCell = row.createCell((short) 0);
			resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultNameCell.setCellValue("Gesture type:");
			resultNameCell.setCellStyle(cellStyle);
			resultValueCell = row.createCell((short) 1);
			resultValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultValueCell.setCellValue("down:" + readerDelta.get(0) + "ms");
			resultValueCell = row.createCell((short) 2);
			resultValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultValueCell.setCellValue("up:" + readerDelta.get(readerDelta.size() - 1) + "ms");
			Integer responeTime = (int) (((UXUtil.getFrameTimeStamps(frameList.get(totalFrames - 1)) * UXConstants.MULTIPLE) - UXUtil.getInputReaderList().get((UXUtil.getInputReaderList().size() - 1))) / 1000);
			row = sheet.createRow(totalFrames + 6);
			resultNameCell = row.createCell((short) 0);
			resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultNameCell.setCellValue("Response time:");
			resultNameCell.setCellStyle(cellStyle);
			resultValueCell = row.createCell((short) 1);
			resultValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultValueCell.setCellValue(responeTime + "(ms)");
			if (powerReport != null) {
				List<Power> list = UXUtil.getPowerLog(powerReport);
				double sumPower = 0.0;
				for (int j = 0; j < list.size(); j++) {
					sumPower += list.get(j).getAvgPower();
				}
				double d1 = sumPower * (list.get(list.size() - 1).getTime() - list.get(0).getTime()) / (60 * 60 * 60 * 1000);
				BigDecimal b1 = new BigDecimal(d1);
				Double powerConsumption = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				row = sheet.createRow(totalFrames + 7);
				resultNameCell = row.createCell((short) 0);
				resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				resultNameCell.setCellValue("Power consumption:");
				resultNameCell.setCellStyle(cellStyle);
				resultValueCell = row.createCell((short) 1);
				resultValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				resultValueCell.setCellValue(powerConsumption + "(mAh)");
			}
			row = sheet.createRow(totalFrames + 8);
			resultNameCell = row.createCell((short) 0);
			resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultNameCell.setCellValue("IO throughput:");
			resultNameCell.setCellStyle(cellStyle);
			resultValueCell = row.createCell((short) 1);
			resultValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultValueCell.setCellValue("IO result");

			row = sheet.createRow(totalFrames + 9);
			resultNameCell = row.createCell((short) 0);
			resultNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			resultNameCell.setCellValue("Animations:");
			resultNameCell.setCellStyle(cellStyle);
			FileOutputStream fOut = new FileOutputStream(logReport);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void DealWithPRCImage(String basePath){
		try{
		    File tempFile = new File(basePath + UXConstants.CAMERALOGFILE);
		    if(!tempFile.exists()){
		    	Util.copyDirectiory(basePath+"/dst", basePath+"/result");
		    	File dstFile = new File(basePath+"/result");
		    	File[] subDstFiles = dstFile.listFiles();
		    	for(int i=0;i<subDstFiles.length;i++){
		    		File subDstFile = new File(basePath+"/result/"+Util.padding(String.valueOf(i), "0", 7));
		    		subDstFiles[i].renameTo(subDstFile);
		    	}
		    	File resultPath = new File(basePath+"/FCBasedResult");
		    	resultPath.mkdirs();
		    	String cmd = "";
		    	String systemType = System.getProperties().getProperty("os.name");
		    	if(systemType.contains("Windows")){
		    		cmd = "dir"+basePath+"/result  /b > "+basePath + UXConstants.CAMERALOGFILE;
		    	}else{
		    		cmd = "ls "+basePath+"/result  > "+basePath + UXConstants.CAMERALOGFILE;
		    	}
		    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
		    	System.out.println(cmd);
		    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
		    	Process pro = Runtime.getRuntime().exec(cmd);
		    	pro.waitFor();
		    	BufferedInputStream br1 = new BufferedInputStream(pro.getInputStream());
				BufferedInputStream br2 = new BufferedInputStream(pro.getErrorStream());
				StringBuffer text = new StringBuffer("获得的信息是: \n");
				int ch;
				while ((ch = br1.read()) != -1) 
					text.append((char)ch);
				StringBuffer text1 = new StringBuffer("获得的错误信息是: \n");
				while ((ch = br2.read()) != -1) 
					text1.append((char)ch);
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				//System.out.println(text.toString());
				System.out.println(text1.toString());
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				
		    	File resultFile = new File(basePath + UXConstants.CAMERALOGFILE);
		    	if(resultFile!=null){
		    		StringBuilder sbTemp = new StringBuilder();
		    		String line = "";
		    		BufferedReader br = new BufferedReader(new FileReader(resultFile));
		    		while((line = br.readLine())!=null){
		    			sbTemp.append(line).append(" 0\n");
		    		}
		    		br.close();
		    		FileWriter fw = new FileWriter(basePath + UXConstants.CAMERALOGFILE);
		    		fw.write(sbTemp.toString());
		    		fw.flush();
		    		fw.close();
		    	}
		    }
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
	}
}
