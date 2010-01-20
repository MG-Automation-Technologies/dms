package com.openkm.test;

import java.io.File;
import java.net.URLEncoder;

import com.openkm.api.OKMAuth;
import com.openkm.api.OKMFolder;
import com.openkm.bean.ContentInfo;
import com.openkm.module.direct.DirectRepositoryModule;
import com.openkm.util.JCRUtils;

public class Test {	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//RepositoryUpgrade.main(new String[] {"null/repository.xml", "null/repository"});
		//Config.load();
		//Dummy.main(null);
		//DummyVersion.main(null);
		//DummyLockToken.main(null);
		//DummyLockAccessDenied.main(null);
		//IsCheckedOutTest.main(null);
		//new BasicTest().basic();
		//new BasicTest().simple();
		//new BasicTest().complex();
		//new BasicTest().move();
		//new BasicTest().copy();
		//new BasicTest().renameDocument();
		//new BasicTest().renameFolder();
		//new BasicTest().folderContentInfo();
		//new BasicTest().search();
		//new BasicTest().searchSave();
		//new BasicTest().searchPagination();
		//new BasicTest().utf8();
		//new RemoveTest().simple();
		//new RemoveTest().multimpleDocs();
		//new RemoveTest().multimpleFolders();
		//new RemoveTest().complex();
		//new RemoveTest().purge();
		//new RemoveTest().purgeTrash();
		//new LockingTest().simple();
		//new LockingTest().multiple();
		//new LockingTest().intersession();
		//new VersioningTest().simple();
		//new VersioningTest().complete();
		//new VersioningTest().concurrent();
		//new VersioningTest().multiuser();
		//new VersioningTest().restore();
		//new VersioningTest().restoreAndCommit();
		//new VersioningTest().intersession();
		//new VersioningTest().cancelCheckout();
		//new AuthTest().basic();
		//new AuthTest().simple();
		//new PropertyGroupTest().simple();
		//new NotificationTest().basic();
		//new NotificationTest().simple();
		//new NotificationTest().complex();
		//new NotificationTest().lock();
		//new BookmarkTest().basic();
		//new BookmarkTest().simple();
		//new LanguageDetect().simple();
		//new DashboardTest().basic();
		//new DashboardTest().simple();
		//misc();
		//DirectRepositoryModule.shutdownRepository();
		//System.out.println(System.getProperty("file.encoding"));
		//-Dfile.encoding=ISO-8859-1
		//String name = "pepe.txt";
		//System.out.println(FileUtils.getMimeType(name));
		//String msg = com.openkm.util.Update.query(UUIDGenerator.generate(log));
		//System.err.println(msg);
		//System.out.println("Es: "+Pattern.matches(wildcard2regexp("*d~"), "pepe.c~"));
		//String x = Update.query("835545549206998028514158635110");
		//System.out.println("'"+x+"'");
		
		//Config.JBOSS_HOME = "/home/pavila/Kirofano/jboss-4.2.2.GA";
		//HashMap<String, MetaData> xx = DirectPropertyGroupModule.parseMetadata();
		//System.out.println(xx);
		
		//System.out.println(com.openkm.util.Mail.getTinyUrl("http://www.kde.org"));
		
		String path = "/uno/dos/tres";
		//System.out.println(getPath(path));
		System.out.println(getPath("/ee/we"));
	}
	
	public static String getPath(String path) throws Exception {
		int idx = path.lastIndexOf('/');
		
		if (idx > 0) {
			String name = path.substring(idx+1);
			String parent = path.substring(0, idx);
			System.out.println(parent+" - "+name);
			return getPath(parent)+" / "+"<a href=\"repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8")+"\">"+name+"</a>";
		} else {
			if (!path.substring(1).equals("")) {
				return "/ <a href=\"repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8")+"\">"+path.substring(1)+"</a>";
			} else {
				return "/";
			}
		}
	}
		
	public static void misc() throws Exception {
		//log.debug("************ REMOVE: BEGIN ********");
		new DirectRepositoryModule().remove();
		//log.debug("************ REMOVE: END ********");
		//log.info("************ CREATE: BEGIN ********");
		String okmRoot = DirectRepositoryModule.initialize();
		//log.info("************ CREATE: END ********");
		//log.info("************ LOGIN: BEGIN ********");
		String token = OKMAuth.getInstance().login("paco","pepe");
		//log.info("************ LOGIN: END ********");
		//log.debug("************ GET_ROOT: BEGIN ********");
		//String okmRoot = new OKMRepository().getRootPath(token);
		//log.debug("************ GET_ROOT: END ********");
		//log.debug("************ DUMP: END ********");
		//new OKMRepository().dump(token, okmRoot);
		//log.debug("************ DUMP: END ********");
		//log.info("************ IMPORT: BEGIN ********");
		//long start = Calendar.getInstance().getTimeInMillis();
		JCRUtils.importFolder(token, okmRoot, new File("documentos"), new String[]{ "doc", "pdf", "txt" });
		
		//String x = new OKMRepository().export(token);
		//String x = new OKMRepository().dump(token, null);
		//System.out.println(x);
		//FileOutputStream fos = new FileOutputStream("export.xml");
		//fos.write(x.getBytes());
		//fos.close();
		ContentInfo ci = OKMFolder.getInstance().getContentInfo(token, okmRoot);
		System.out.println(ci);
		
		OKMAuth.getInstance().logout(token);
	}
}
