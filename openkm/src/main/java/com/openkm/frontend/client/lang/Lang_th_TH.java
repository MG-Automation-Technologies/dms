/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.frontend.client.lang;

import java.util.HashMap;

import com.openkm.frontend.client.config.ErrorCode;

/**
 * Thai (Thailand)
 * 
 * @author Chidchanok Sa-ngamuang (chidchanoksa@sentplus.com)
 */
public class Lang_th_TH {
	
	public final static HashMap<String, String> lang;
	  static {
	    lang = new HashMap<String, String>();
	    
	    // General configuration
	    lang.put("general.date.pattern", "dd-mm-yyyy hh:mm:ss");
	    lang.put("general.day.pattern", "dd-mm-yyyy");
	    lang.put("general.hour.pattern", "HH:mm:ss");
	    
	    // Startup
	    lang.put("startup.openkm", "กำลังโหลด OpenKM");
	    lang.put("startup.starting.loading", "กำลังเริ่มต้นการโหลด OpenKM");
	    lang.put("startup.taxonomy", "กำลังจัดเตรียมประเภทเอกสารตั้งต้น");
	    lang.put("startup.categories", "กำลังจัดเตรียมหมวดหมู่เอกสารตั้งต้น");
	    lang.put("startup.thesaurus", "กำลังจัดเตรียมพจนานุกรมคำที่มีความหมายใกล้เคียง");
	    lang.put("startup.template", "กำลังจัดเตรียมรูปแบบร่างตั้งต้น");
	    lang.put("startup.personal", "กำลังจัดเตรียมเอกสารส่วนบุคคล");
	    lang.put("startup.mail", "กำลังจัดเตรียมระบบอีเมล");
	    lang.put("startup.trash", "กำลังจัดเตรียมระบบถังขยะ");
	    lang.put("startup.user.home", "กำลังจัดเตรียมพื้นที่สำหรับผู้ใช้งาน");
	    lang.put("startup.bookmarks", "กำลังจัดเตรียม bookmarks");
	    lang.put("startup.loading.taxonomy", "กำลังโหลดประเภทเอกสาร");
	    lang.put("startup.loading.taxonomy.getting.folders", "กำลังโหลดประเภทเอกสาร - โฟลเดอร์");
	    lang.put("startup.loading.taxonomy.eval.params", "กำลังโหลดประเภทเอกสาร - eval browser params");
	    lang.put("startup.loading.taxonomy.open.path", "กำลังโหลดประเภทเอกสาร - open path");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.folders", "กำลังโหลดประเภทเอกสาร - โฟลเดอร์และรายการแฟ้มข้อมูล");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.documents", "กำลังโหลดประเภทเอกสาร - รายชื่อเอกสาร");
	    lang.put("startup.loading.taxonomy.getting.filebrowser.mails", "กำลังโหลดประเภทเอกสาร - อีเมล");
	    lang.put("startup.loading.personal", "กำลังโหลดข้อมูลส่วนบุคคล");
	    lang.put("startup.loading.mail", "กำลังโหลดอีเมล");
	    lang.put("startup.loading.categories", "กำลังโหลดหมวดหมู่");
	    lang.put("startup.loading.thesaurus", "กำลังโหลดพจนานุกรมคำที่มีความหมายใกล้เคียง");
	    lang.put("startup.loading.templates", "กำลังโหลดแบบร่าง");
	    lang.put("startup.loading.trash", "กำลังโหลดถังขยะ");
	    lang.put("startup.loading.history.search", "กำลังโหลดประวัติการสืบค้น");
	    lang.put("startup.loading.user.values", "กำลังโหลดค่าของยูสเซอร์");
	    lang.put("startup.keep.alive", "กำลังโหลด keep alive");
	    
	    // Update notification
	    lang.put("openkm.update.available", "รายการอัพเดท OpenKM");
	    
	    // Left Panel
	    lang.put("leftpanel.label.taxonomy", "ประเภท");
	    lang.put("leftpanel.label.trash", "ถังขยะ");
	    lang.put("leftpanel.label.mail", "อีเมลl");
	    lang.put("leftpanel.label.stored.search", "การสืบค้นที่ถูกเก็บไว้");
	    lang.put("leftpanel.label.categories", "หมวดหมู่");
	    lang.put("leftpanel.label.thesaurus", "พจนานุกรมคำพ้อง");
	    lang.put("leftpanel.label.templates", "แบบร่าง");
	    lang.put("leftpanel.label.my.documents", "เอกสารของฉัน");
	    lang.put("leftpanel.label.user.search", "ข่าวสารผู้ใช้งาน");
	    lang.put("leftpanel.label.all.repository", "คลังเอกสารทั้งหมด");
	    
	    // Tree
	    lang.put("tree.menu.directory.create", "สร้างโฟลเดอร์");
	    lang.put("tree.menu.directory.remove", "ลบ");
	    lang.put("tree.menu.directory.rename", "เปลี่ยนชื่อ");
	    lang.put("tree.menu.directory.refresh", "รีเฟรช");
	    lang.put("tree.menu.directory.move", "ย้าย");
	    lang.put("tree.menu.directory.copy", "ทำสำเนา");
	    lang.put("tree.menu.directory.add.document", "เพิ่มเอกสาร");
	    lang.put("tree.menu.add.bookmark", "เพิ่ม bookmark");
	    lang.put("tree.menu.set.home", "ตั้งค่าหน้าตั้งต้น");
	    lang.put("tree.menu.export", "ส่งออกไปที่ไฟล์");
	    lang.put("tree.status.refresh.folder", "ปรับปรุงรายการโฟลเดอร์");
	    lang.put("tree.status.refresh.create", "กำลังสร้างโฟลเดอร์");
	    lang.put("tree.status.refresh.delete", "กำลังลบโฟลเดอร์");
	    lang.put("tree.status.refresh.rename", "กำลังเปลี่ยนชื่อโฟลเดอร์");
	    lang.put("tree.status.refresh.restore", "กำลังดึงโฟลเดอร์กลับ");
	    lang.put("tree.status.refresh.purge", "กำลังล้างโฟลเดอร์");
	    lang.put("tree.status.refresh.get", "กำลังปรับปรุงโฟลเดอร์");
	    lang.put("tree.folder.new", "โฟลเดอร์ใหม่");
	    lang.put("tree.status.refresh.add.subscription", "กำลังเพิ่มการเป็นสมาชิก");
	    lang.put("tree.status.refresh.remove.subscription", "กำลังลบการเป็นสมาชิก");
	    lang.put("tree.status.refresh.get.root", "กำลังรีเฟรชโฟลเดอร์ตั้งต้น");
	    lang.put("tree.status.refresh.get.keywords", "กำลังรีเฟรช keywords");
	    lang.put("tree.status.refresh.get.user.home", "กำลังเตรียมหน้าหลักสำหรับยูสเซอร์");
	    lang.put("tree.status.refresh.purge.trash", "กำลังลบข้อมูลในถังขยะ");
	    lang.put("tree.menu.directory.find.folder","ค้นหาโฟลเดอร์");
	    
	    // Trash
	    lang.put("trash.menu.directory.restore", "ดึงกลับ");
	    lang.put("trash.menu.directory.purge", "ทำลาย");
	    lang.put("trash.menu.directory.purge.trash", "ทำลายข้อมูลในถังขยะ");
	    lang.put("trash.directory.select.label", "เลือกโฟลเดอร์ปลายทาง");
	    
	    // General menu
	    lang.put("general.menu.file", "แฟ้ม");
	    	lang.put("general.menu.file.exit", "ออกจากโปรแกรม");
	    	lang.put("general.menu.file.purge.trash", "ทำลายข้อมูลในถังขยะ");
	    lang.put("general.menu.edit", "แก้ไข");
			lang.put("general.menu.file.create.directory", "สร้างโฟลเดอร์");
			lang.put("general.menu.file.download.document", "ดาวน์โหลดเอกสาร");
			lang.put("general.menu.file.download.document.pdf", "ดาวน์โหลดเอกสารเป็น PDF");
			lang.put("general.menu.file.send.link", "ส่งลิ้งค์ของเอกสาร");
			lang.put("general.menu.file.send.attachment", "ส่งเอกสารแนบ");
			lang.put("general.menu.file.lock", "ล็อค");
			lang.put("general.menu.file.unlock", "ปลดล็อค");
			lang.put("general.menu.file.add.document", "เพิ่มเอกสาร");
			lang.put("general.menu.file.checkout", "เช็คเอ้าต์");
			lang.put("general.menu.file.checkin", "เช็คอิน");
			lang.put("general.menu.file.cancel.checkout", "ยกเลิกการเช็คเอ้าต์");
			lang.put("general.menu.file.delete", "ลบ");
			lang.put("general.menu.file.refresh", "รีเฟรซ");
			lang.put("general.menu.file.scanner", "เครื่องสแกนเอกสาร");
			lang.put("general.menu.file.uploader", "ตัวอัพโหลดแฟ้ม");
	    lang.put("general.menu.tools", "เครื่องมือ");
	    	lang.put("general.menu.tools.languages", "ภาษา");
	    	lang.put("general.menu.tools.skin", "สกิน");
    			lang.put("general.menu.tools.skin.default", "ค่า default");
    			lang.put("general.menu.tools.skin.default2", "ค่า default 2");
    			lang.put("general.menu.tools.skin.mediumfont", "ฟอนด์ขนาดกลาง");
    			lang.put("general.menu.tools.skin.bigfont", "ฟอนด์ขนาดใหญ่");
    		lang.put("general.menu.debug.console", "คอนโซลแก้ Bug");
    		lang.put("general.menu.administration", "แสดงหน้าผู้ดูแลระบบ");
    		lang.put("general.menu.tools.preferences", "ค่าที่ต้องการ(Preferences)");
    			lang.put("general.menu.tools.user.preferences", "การตั้งค่าของยูสเซอร์");
    	lang.put("general.menu.bookmark", "Bookmarks");
	    	lang.put("general.menu.bookmark.home", "หน้าหลัก");
	    	lang.put("general.menu.bookmark.default.home", "ตั้งให้เป็นหน้าหลัก");
	    	lang.put("general.menu.bookmark.edit", "แก้ไข bookmarks");
	    lang.put("general.menu.help", "ช่วยเหลือ");
		    lang.put("general.menu.bug.report", "รายงาน Bug");
		    lang.put("general.menu.support.request", "ขอความช่วยเหลือ");
		    lang.put("general.menu.public.forum", "Forum สาธารณะ");
		    lang.put("general.menu.project.web", "เว็บโครงงาน");
		    lang.put("general.menu.version.changes", "บันทึกเกี่ยวกับเวอร์ชั่น");
		    lang.put("general.menu.documentation", "เอกสาร");
		    lang.put("general.menu.about", "เกี่ยวกับ OpenKM");
	    lang.put("general.connected", "ถูกเชื่อมต่อเป็น");
	    
	    // File Browser
	    lang.put("filebrowser.name", "ชื่อ");
	    lang.put("filebrowser.date.update", "วันที่ปรับปรุง");
	    lang.put("filebrowser.size", "ขนาด");
	    lang.put("filebrowser.path", "เส้นทาง");
	    lang.put("filebrowser.author", "ผู้แต่ง");
	    lang.put("filebrowser.version", "เวอร์ชั่น");
	    lang.put("filebrowser.menu.checkout", "เช็คเอ้าต์");
	    lang.put("filebrowser.menu.checkin", "เช็คอิน");
	    lang.put("filebrowser.menu.delete", "ลบ");
	    lang.put("filebrowser.menu.rename", "เปลี่ยนชื่อ");
	    lang.put("filebrowser.menu.checkout.cancel", "ยกเลิกการเช็คเอ้าต์");
	    lang.put("filebrowser.menu.lock", "ล็อค");
	    lang.put("filebrowser.menu.unlock", "ปลดล็อค");
	    lang.put("filebrowser.menu.download", "ดาวน์โหลด");
	    lang.put("filebrowser.menu.move", "ย้าย");
	    lang.put("filebrowser.menu.copy", "สำเนา");
	    lang.put("filebrowser.menu.create.from.template", "สร้างใหม่จากแบบร่าง");
	    lang.put("filebrowser.menu.add.property.group", "เพิ่มคุณสมบัติของกลุ่ม");
	    lang.put("filebrowser.menu.remove.property.group", "ออกจากคุณสมบัติของกลุ่ม");
	    lang.put("filebrowser.menu.start.workflow", "เริ่มต้นเวิร์คโฟลว์");
	    lang.put("filebrowser.menu.add.subscription", "เพิ่มการเป็นสมาชิก");
	    lang.put("filebrowser.menu.remove.subscription", "ออกจากการเป็นสมาชิก");
	    lang.put("filebrowser.menu.add.bookmark", "เพิ่ม bookmark");
	    lang.put("filebrowser.menu.set.home", "ตั้งเป็นหน้าหลัก");
	    lang.put("filebrowser.menu.refresh", "รีเฟรซ");
	    lang.put("filebrowser.menu.export", "ส่งออกเป็น ZIP ไฟล์");
	    lang.put("filebrowser.menu.play", "เล่น");
	    lang.put("filebrowser.menu.image.viewer", "ดูภาพ");
	    lang.put("filebrowser.status.refresh.folder", "กำลังปรับปรุงรายการโฟลเดอร์");
	    lang.put("filebrowser.status.refresh.document", "กำลังปรับปรุงรายการเอกสาร");
	    lang.put("filebrowser.status.refresh.mail", "กำลังปรับปรุงรายการอีเมล");
	    lang.put("filebrowser.status.refresh.delete.folder", "กำลังลบโฟลเดอร์");
	    lang.put("filebrowser.status.refresh.delete.document", "กำลังลบเอกสาร");
	    lang.put("filebrowser.status.refresh.checkout", "กำลังเช็คเอ้าต์เอกสาร");
	    lang.put("filebrowser.status.refresh.lock", "กำลังล็อคเอกสาร");
	    lang.put("filebrowser.status.refresh.unlock", "กำลังปลดล็อคเอกสาร");
	    lang.put("filebrowser.status.refresh.document.rename", "กำลังเปลี่ยนชื่อเอกสาร");
	    lang.put("filebrowser.status.refresh.folder.rename", "กำลังเปลี่ยนชื่อโฟลเดอร์");
	    lang.put("filebrowser.status.refresh.document.purge", "กำลังลบเอกสาร");
	    lang.put("filebrowser.status.refresh.folder.purge", "กำลังลบโฟลเดอร์");
	    lang.put("filebrowser.status.refresh.folder.get", "กำลังปรับปรุงโฟลเดอร์");
	    lang.put("filebrowser.status.refresh.document.get", "กำลังปรับปรุงเอกสาร");
	    lang.put("filebrowser.status.refresh.add.subscription", "กำลังเพิ่มเป็นสมาชิก");
	    lang.put("filebrowser.status.refresh.remove.subscription", "กำลังลบออกจากสมาชิก");
	    lang.put("filebrowser.status.refresh.get.user.home", "กำลังไปสู่หน้าหลัก");
	    lang.put("filebrowser.status.refresh.delete.mail", "กำลังลบเมล");
	    lang.put("filebrowser.status.refresh.mail.purge", "กำลังลบเมล");
	    
	    // File Upload
	    lang.put("fileupload.send", "อัพโหลด");
	    lang.put("fileupload.status.sending", "กำลังอัพโหลดไฟล์...");
	    lang.put("fileupload.status.indexing", "กำลังทำดัชนีไฟล์...");
	    lang.put("fileupload.status.ok", "ไฟล์ได้ถูกอัพโหลดอย่างถูกต้อง");
	    lang.put("fileupload.upload.status", "สถานะการอัพโหลด");
	    lang.put("fileupload.upload.uploaded", "อัพโหลดแล้ว");
	    lang.put("fileupload.upload.completed", "อัพโหลดเสร็จแล้ว");
	    lang.put("fileupload.upload.runtime", "เวลาที่ใช้");
	    lang.put("fileupload.upload.remaining", "ส่วนที่เหลือ");
	    lang.put("fileupload.button.close", "ปิด");
	    lang.put("fileupload.button.add.other.file", "เพิ่มไฟล์อื่นๆ");
	    lang.put("fileupload.status.move.file", "กำลังย้ายไฟล์...");
	    lang.put("fileupload.status.move.mail", "กำลังย้ายเมล...");
	    lang.put("fileupload.status.copy.file", "กำลังทำสำเนาไฟล์...");
	    lang.put("fileupload.status.copy.mail", "กำลังทำสำเนาเมล...");
	    lang.put("fileupload.status.restore.file", "กำลังดึงกลับไฟล์...");
	    lang.put("fileupload.status.restore.mail", "กำลังดึงกลับเมล...");
	    lang.put("fileupload.status.move.folder", "กำลังย้ายโฟลเดอร์...");
	    lang.put("fileupload.status.copy.folder", "กำลังทำสำเนาโฟลเดอร์...");
	    lang.put("fileupload.status.restore.folder", "กำลังดึงกลับโฟลเดอร์...");
	    lang.put("fileupload.status.create.from.template", "กำลังสร้างไฟล์จากแบบร่าง...");
	    lang.put("fileupload.status.of", "จาก");
	    lang.put("fileupload.label.insert", "เพิ่มเอกสารใหม่");
	    lang.put("fileupload.label.update", "ปรับปรุงเอกสาร");
	    lang.put("fileupload.label.users.notify", "แจ้งผู้ใช้งาน");
	    lang.put("fileupload.label.comment", "ข้อคิดเห็น");
	    lang.put("fileupload.label.users.to.notify",  "ผู้ใช้งานที่แจ้งเตือน");
	    lang.put("fileupload.label.users",  "ผู้ใช้งาน");
	    lang.put("fileupload.label.groups.to.notify","กลุ่มที่แจ้งเตือน");
	    lang.put("fileupload.label.groups","กลุ่ม");
	    lang.put("fileupload.label.must.select.users",  "คุณต้องเลือกผู้ใช้งานที่จะแจ้งเตือน");
	    lang.put("fileupload.label.importZip", "นำเข้าเอกสารจาก ZIP ไฟล์");
	    lang.put("fileupload.label.notify.comment", "แจ้งความคิดเห็น");
	    lang.put("fileupload.label.error.importing.zip", "มีการผิดพลาดในการนำเข้าไฟล์");
	    lang.put("fileupload.label.error.move.file", "มีการผิดพลาดในการย้ายไฟล์");
	    lang.put("fileupload.label.error.move.mail", "มีการผิดพลาดในการย้ายเมล");
	    lang.put("fileupload.label.error.copy.file", "มีการผิดพลาดในการทำสำเนาไฟล์");
	    lang.put("fileupload.label.error.copy.mail", "มีการผิดพลาดในการทำสำเนาเมล");
	    lang.put("fileupload.label.error.restore.file", "มีการผิดพลาดในการดึงกลับไฟล์");
	    lang.put("fileupload.label.error.restore.mail", "มีการผิดพลาดในการดึงกลับเมล");
	    lang.put("fileupload.label.error.move.folder", "มีการผิดพลาดในการย้ายโฟลเดอร์");
	    lang.put("fileupload.label.error.copy.folder", "มีการผิดพลาดในการทำสำเนาโฟลเดอร์");
	    lang.put("fileupload.label.error.restore.folder", "มีการผิดพลาดในการดึงกลับโฟลเดอร์");
	    lang.put("fileupload.label.error.create.from.template", "มีการผิดพลาดในการสร้างไฟล์จากแบบร่าง");
	    lang.put("fileupload.label.error.not.allowed.move.folder.child", "ไม่ยินยอมให้เคลื่อนย้ายทับโฟลเดอร์ต้นฉบับและโฟลเดอร์ภายใต้นั้น");
	    lang.put("fileupload.label.error.not.allowed.copy.same.folder", "ไม่ยินยอมให้ทำสำเนาทับโฟลเดอร์ต้นฉบับ");
	    lang.put("fileupload.label.error.not.allowed.create.from.template.same.folder", "ไม่ยินยอมให้มีการสร้างไฟล์บนโฟลเดอร์ต้นฉบับ");
	    
	    // Tab properties
	    lang.put("tab.document.properties", "คุณสมบัติ");
	    lang.put("tab.document.notes", "บันทึก");
	    lang.put("tab.document.history", "ประวัติ");
	    lang.put("tab.document.status.history", "ประวัติการปรับปรุง");
	    lang.put("tab.status.security.role", "กำลังปรับปรุงข้อมูลความปลอดภัย");
	    lang.put("tab.status.security.user", "กำลังปรับปรุงข้อมูลความปลอดภัยของผู้ใช้งาน");
	    lang.put("tab.document.status.group.properties", "กำลังปรับปรุงคุณสมบัติของกลุ่ม");
	    lang.put("tab.document.status.set.keywords", "กำลังตั้งค่าคำค้นหา");
	    lang.put("tab.document.status.set.categories", "กำลังปรับปรุงหมวดหมู่เอกสาร");
	    lang.put("tab.document.status.get.version.history.size", "กำลังรีเฟรซขนาดของประวัติเอกสาร");
	    lang.put("tab.document.status.purge.version.history", "กำลังปรับลดขนาดของประวัติเอกสาร");
	    lang.put("tab.document.status.restore.version", "กำลังดึงกลับเวอร์ชั่นของเอกสาร");
	    lang.put("tab.document.security", "ความปลอดภัย");
	    lang.put("tab.document.preview", "ดูออนไลน์");
	    lang.put("tab.folder.properties", "คุณสมบัติ");
	    lang.put("tab.folder.security", "ความปลอดภัย");
	    
	    // Workspace tabs
	    lang.put("tab.workspace.desktop", "หน้างาน");
	    lang.put("tab.workspace.search", "สืบค้น");
	    lang.put("tab.workspace.dashboard", "สถานะโดยรวม");
	    lang.put("tab.workspace.administration", "การดูแลระบบ");
	    
	    //  Document
	    lang.put("document.uuid", "UUID");
	    lang.put("document.name", "ชื่อ");
	    lang.put("document.folder", "โฟลเดอร์");
	    lang.put("document.size", "ขนาด");
	    lang.put("document.created", "ถูกสร้างแล้ว");
	    lang.put("document.lastmodified", "ถูกแก้ไขแล้ว");
	    lang.put("document.mimetype", "ชนิด MIME");
	    lang.put("document.keywords", "คำค้นหา");
	    lang.put("document.by", "โดย");
	    lang.put("document.status", "สถานะ");
	    lang.put("document.status.checkout", "ถูกแก้ไขโดย");
	    lang.put("document.status.locked", "ถูกล็อคโดย");
	    lang.put("document.status.normal", "พร้อมใช้");
	    lang.put("document.subscribed", "เป็นสมาชิก");
	    lang.put("document.subscribed.yes", "ตกลง");
	    lang.put("document.subscribed.no", "ไม่ตกลง");
	    lang.put("document.history.size", "ขนาดของประวัติ");
	    lang.put("document.subscribed.users", "ผู้ใช้ที่เป็นสมาชิก");
	    lang.put("document.url", "URL");
	    lang.put("document.webdav", "WebDAV");
	    lang.put("document.add.note", "เพิ่มข้อความ");
	    lang.put("document.keywords.cloud", "กลุ่มคำที่ใช้ค้นหา");
	    lang.put("document.categories", "หมวดหมู่");
	    
	    // Folder
	    lang.put("folder.uuid", "UUID");
	    lang.put("folder.name", "ชื่อ");
	    lang.put("folder.parent", "Parent");
	    lang.put("folder.created", "ถูกสร้างแล้ว");
	    lang.put("folder.by", "โดย");
	    lang.put("folder.subscribed", "เป็นสมาชิก");
	    lang.put("folder.subscribed.yes", "ใช่");
	    lang.put("folder.subscribed.no", "ไม่ใช่");
	    lang.put("folder.subscribed.users", "ผู้ใช้ที่เป็นสมาชิก");
	    lang.put("folder.url", "URL");
	    lang.put("folder.webdav", "WebDAV");
	    lang.put("folder.number.folders", "โฟลเดอร์");
	    lang.put("folder.number.documents", "เอกสาร");
	    lang.put("folder.number.mails", "เมล");
	    
	    // Version
	    lang.put("version.name", "เวอร์ชั่น");
	    lang.put("version.created", "วันที่");
	    lang.put("version.author", "ผู้เขียน");
	    lang.put("version.size", "ขนาด");
	    lang.put("version.purge.document", "ประวัติการบีบลดขนาด");
	    lang.put("version.comment", "ข้อคิดเห็น");
	    
	    // Security
	    lang.put("security.label", "รักษาความปลอดภัย");
	    lang.put("security.group.name", "กลุ่ม");
	    lang.put("security.group.permission.read", "อ่าน");
	    lang.put("security.group.permission.write", "เขียน");
	    lang.put("security.group.permission.delete", "ลบ");
	    lang.put("security.group.permission.security", "รักษาความปลอดภัย");
	    lang.put("security.user.name", "ผู้ใชังาน");
	    lang.put("security.user.permission.read", "อ่าน");
	    lang.put("security.user.permission.write", "เขียน");
	    lang.put("security.user.permission.delete", "ลบ");
	    lang.put("security.user.permission.security", "รักษาความปลอดภัย");
	    lang.put("security.users", "ผู้ใช้งาน");
	    lang.put("security.groups", "กลุ่ม");
	    lang.put("security.recursive", "ให้สิทธิ์ในลำดับชั้นที่ลึกลงไป");
	    lang.put("secutiry.filter.by.users","กรองผู้ใช้งาน");
	    lang.put("secutiry.filter.by.groups","กรองกลุ่มผู้ใช้งาน");
	    lang.put("security.status.updating","ปรับปรุงระบบรักษาความปลอดภัย");
	    
	    // Preview
	    lang.put("preview.unavailable", "ไม่สามารถดูออนไลน์ได้");

	    // Mail
	    lang.put("mail.from", "จาก");
	    lang.put("mail.reply", "ตอบถึง");
	    lang.put("mail.to", "ถึง");
	    lang.put("mail.subject", "ชื่อเรื่อง");
	    lang.put("mail.attachment", "เอกสารแนบ");
	    
	    // Error
	    lang.put("error.label", "ระบบให้ผลลัพธ์เป็นข้อผิดพลาด");
	    lang.put("error.invocation", "มีข้อผิดพลาดในการสื่อสารกับเซิฟเวอร์");
	    
	    // About
	    lang.put("about.label", "เกี่ยวกับ OpenKM");
	    lang.put("about.loading", "กำลังโหลด ...");
	    
	    // Logout
	    lang.put("logout.label", "ออกจากโปรแกรม");
	    lang.put("logout.closed", "OpenKM ถูกปิดอย่างถูกต้อง.");
	    lang.put("logout.logout", "กำลังออกจากระบบ OpenKM โปรดรอสักครู่");
	    
	    // Confirm
	    lang.put("confirm.label", "การยืนยัน");
	    lang.put("confirm.delete.folder", "¿ คุณยังต้องการลบโฟลเดอร์หรือไม่ ?");
	    lang.put("confirm.delete.document", "¿ คุณยังต้องการลบเอกสารหรือไม่ ?");
	    lang.put("confirm.delete.trash", "¿ คุณยังต้องการเคลียร์ถังขยะหรือไม่ ?");
	    lang.put("confirm.purge.folder", "¿ คูณยังต้องการลบโฟลเดอร์หรือไม่ ?");
	    lang.put("confirm.purge.document", "¿ คุณยังต้องการลบเอกสารหรือไม่ ?");
	    lang.put("confirm.delete.propety.group", "¿ คุณยังต้องการลบคุณสมบัติของกลุ่มหรือไม่ ?");
	    lang.put("confirm.purge.version.history.document", "¿ คุณยังต้องการลบประวัติของเอกสารหรือไม่ ?");
	    lang.put("confirm.purge.restore.document", "¿ คุณยังต้องการดึงเอกสารเวอร์ชั่นนี้กลับหรือไม่ ?");
	    lang.put("confirm.set.default.home", "¿ คุณยังต้องการตั้งให้เป็นหน้าประจำหลักหรือไม่ ?");
	    lang.put("confirm.delete.saved.search", "¿ คุณยังต้องการลบรูปแบบการสืบค้นที่ได้เก็บไว้หรือไม่ ?");
	    lang.put("confirm.delete.user.news", "¿ คุณยังต้องการลบข่าวสารของผู้ใช้งานหรือไม่ ?");
	    lang.put("confirm.delete.mail", "¿ คุณยังคงต้องการลบเมล ?");
	    lang.put("confirm.get.pooled.workflow.task","¿ คุณยังคงต้องการมอบงานนี้ให้กับคุณเอง ?");
	    lang.put("confirm.force.unlock","¿ Are you sure you want to force canceling locked document");
	    lang.put("confirm.force.cancel.checkout","¿ Are you sure you want to force cancelling chekcout document ?");
	    
	    // Search inputs
	    lang.put("search.context", "Context");
	    lang.put("search.content", "เนื้อเอกสาร");
	    lang.put("search.name", "ชื่อเอกสาร");
	    lang.put("search.keywords", "คำค้นหา");
	    lang.put("search.folder", "โฟลเดอร์");
	    lang.put("search.category", "หมวดหมู่");
	    lang.put("search.results", "ผลลัพธ์");
	    lang.put("search.to", "ถึง");
	    lang.put("search.page.results", "หน้าแสดงผลลัพธ์");
	    lang.put("search.add.property.group", "เพิ่มคุณสมบัติกลุ่ม");
	    lang.put("search.mimetype", "ชนิด Mime");
	    lang.put("search.type", "ชนิด");
	    lang.put("search.type.document", "เอกสาร");
	    lang.put("search.type.folder", "โฟลเดอร์");
	    lang.put("search.type.mail", "เมล");
	    lang.put("search.advanced", "การสืบค้นขั้นสูง");
	    lang.put("search.user", "ผู้ใช้งาน");
	    lang.put("search.date.and", "และ");
	    lang.put("search.date.range", "ช่วงระหว่างวันที่");
	    lang.put("search.save.as.news", "บันทึกเป็นข่าวสารผู้ใช้งาน");

	    // search folder filter popup
	    lang.put("search.folder.filter", "กรองโดยโฟลเดอร์");
	    lang.put("search.category.filter", "กรองโดยหมวดหมู่");
	    
	    // Search results
	    lang.put("search.result.name", "ชื่อ");
	    lang.put("search.result.score", "ที่เกี่ยวข้อง");
	    lang.put("search.result.size", "ขนาด");
	    lang.put("search.result.date.update", "วันที่ปรับปรุง");
	    lang.put("search.result.author", "ผู้เขียน");
	    lang.put("search.result.version", "เวอร์ชั่น");
	    lang.put("search.result.path", "เส้นทาง");
	    lang.put("search.result.menu.download", "ดาวน์โหลด");
	    lang.put("search.result.menu.go.folder", "ไปที่โฟลเดอร์");
	    lang.put("search.result.menu.go.document", "ไปที่เอกสาร");
	    lang.put("search.result.status.findPaginated", "กำลังปรับปรุงการสืบค้น");
	    lang.put("search.result.status.runsearch", "กำลังปรับปรุงการสืบค้นที่ถูกจัดเก็บไว้");
	    
	    // Search saved
	    lang.put("search.saved.run", "ทำงาน");
	    lang.put("search.saved.delete", "ลบ");
	    lang.put("search.saved.status.getsearchs", "กำลังรีเฟรซการสืบค้นที่ถูกจัดเก็บไว้");
	    lang.put("search.saved.status.savesearch", "กำลังปรับปรุงการสืบค้นที่ถูกจัดเก็บไว้");
	    lang.put("search.saved.status.deletesearch", "กำลังลบการสืบค้นทีี่ถูกจัดเก็บไว้");
	    lang.put("search.saved.status.getusernews", "กำลังรีเฟรซข่าวสารผู้ใช้งาน");
	    
	    // Button
	    lang.put("button.close", "ปิด");
	    lang.put("button.logout", "ออกจากระบบ");
	    lang.put("button.update", "ปรับปรุง");
	    lang.put("button.cancel", "ยกเลิก");
	    lang.put("button.accept", "ยอมรับ");
	    lang.put("button.restore", "ดึงกลับ");
	    lang.put("button.move", "ย้าย");
	    lang.put("button.change", "เปลี่ยน");
	    lang.put("button.search", "สืบค้น");
	    lang.put("button.save.search", "บันทึกการสืบค้น");
	    lang.put("button.view", "เรียกดู");
	    lang.put("button.clean", "ล้าง");
	    lang.put("button.add", "เพิ่ม");
	    lang.put("button.delete", "ลบ");
	    lang.put("button.copy", "สำเนา");
	    lang.put("button.create", "สร้าง");
	    lang.put("button.show", "แสดง");
	    lang.put("button.memory", "ปรับปรุง");
	    lang.put("button.copy.clipboard", "ทำสำเนาเก็บไว้ที่คลิปบอร์ด");	
	    lang.put("button.start", "เริ่มต้น");
	    lang.put("button.select", "เลือก");
	    lang.put("button.test", "ทดสอบ");
	    lang.put("button.next", "ถัดไป");
	    
	    // Group
	    lang.put("group.label", "เพิ่มคุณสมบัติกลุ่ม");
	    lang.put("group.group", "กลุ่ม");
	    lang.put("group.property.group", "คุณสมบัติ");
	    lang.put("general.menu.file.scanner", "สแกนเนอร์");
	    
	    // Bookmark
	    lang.put("bookmark.label", "เพิ่ม bookmark");
	    lang.put("bookmark.name", "ชื่อ");
	    lang.put("bookmark.edit.label", "แก้ไข bookmarks");
	    lang.put("bookmark.path", "เส้นทาง");
	    lang.put("bookmark.type", "ชนิด");
	    lang.put("bookmark.type.document", "เอกสาร");
	    lang.put("bookmark.type.folder", "โฟลเดอร์");
	    
	    // Notify
	    lang.put("notify.label", "กำลังส่งลิ้งค์เอกสาร");
	    lang.put("notify.label.attachment", "ส่งเอกสารแนบ");
	    
	    // Status
	    lang.put("status.document.copied", "เอกสารถูกระบุทำสำเนา");
	    lang.put("status.document.cut", "เอกสารถูกระบุตัด");
	    lang.put("status.folder.copied", "โฟลเดอร์ถูกระบุทำสำเนา");
	    lang.put("status.folder.cut", "โฟลเดอร์ถูกระบุตัด");
	    lang.put("status.keep.alive.error", "ตรวจพบการขาดการติดต่อกับเซิฟเวอร์ (keep alive)");
	    lang.put("status.debug.enabled", "การแก้ bug เปิดใช้งาน");
	    lang.put("status.debug.disabled", "การแก้ bug ปิดการใช้งาน");
	    lang.put("status.network.error.detected", "ตรวจพบข้อผิดพลาดเกี่ยวกับเครือข่าย");
	    
	    // Calendar
	    lang.put("calendar.day.sunday", "อาทิตย์");
	    lang.put("calendar.day.monday", "จันทร์");
	    lang.put("calendar.day.tuesday", "อังคาร");
	    lang.put("calendar.day.wednesday", "พุธ");
	    lang.put("calendar.day.thursday", "พฤหัส");
	    lang.put("calendar.day.friday", "ศุกร์");
	    lang.put("calendar.day.saturday", "เสาร์");
	    lang.put("calendar.month.january", "มกราคม");
	    lang.put("calendar.month.february", "กุมภาพันธ์");
	    lang.put("calendar.month.march", "มีนาคม");
	    lang.put("calendar.month.april", "เมษายน");
	    lang.put("calendar.month.may", "พฤษภาคม");
	    lang.put("calendar.month.june", "มิถุนายน");
	    lang.put("calendar.month.july", "กรกฎาคม");
	    lang.put("calendar.month.august", "สิงหาคม");
	    lang.put("calendar.month.september", "กันยายน");
	    lang.put("calendar.month.october", "ตุลาคม");
	    lang.put("calendar.month.november", "พฤศจิกายน");
	    lang.put("calendar.month.december", "ธันวาคม");
	    
	    // Media player
	    lang.put("media.player.label", "เครื่องเล่นสื่อ");
	    
	    // Image viewer
	    lang.put("image.viewer.label", "ตัวแสดงภาพ");
	    lang.put("image.viewer.zoom.in", "ซูมเข้า");
	    lang.put("image.viewer.zoom.out", "ซูมออก");
	    
	    // Debug console
	    lang.put("debug.console.label", "แผงแก้ไข bug");
	    lang.put("debug.enable.disable", "กด CTRL+Z เพื่อเปิดหรือปิดโหมดการแก้ bug");

	    // Dashboard tab
	    lang.put("dashboard.tab.general", "ทั่วไป");
	    lang.put("dashboard.tab.news", "ข่าวสาร");
	    lang.put("dashboard.tab.user", "ผู้ใช้งาน");
	    lang.put("dashboard.tab.workflow", "โฟลว์งาน(Workflow)");
	    lang.put("dashboard.tab.mail", "เมล");
	    lang.put("dashboard.tab.keymap", "แผนภูมิคำสืบค้น");
	    
	    // Dahboard general
	    lang.put("dashboard.new.items", "มาใหม่");
	    lang.put("dashboard.user.locked.documents", "เอกสารที่ถูกล็อค");
	    lang.put("dashboard.user.checkout.documents", "เอกสารที่เช็คเอ้าต์");
	    lang.put("dashboard.user.last.modified.documents", "เอกสารที่ถูกแก้ไขล่าสุด");
	    lang.put("dashboard.user.last.downloaded.documents", "เอกสารที่ถูกดาวน์โหลดล่าสุด");
	    lang.put("dashboard.user.subscribed.documents", "เอกสารที่เข้าเป็นสมาชิก");
	    lang.put("dashboard.user.subscribed.folders", "โฟลเดอร์ที่เข้าเป็นสมาชิก");
	    lang.put("dashboard.user.last.uploaded.documents", "เอกสารที่ถูกอัพโหลดล่าสุด");
	    lang.put("dashboard.general.last.week.top.downloaded.documents", "เอกสารที่มีผู้เปิดดูมากที่สุดในสัปดาห์ที่แล้ว");
	    lang.put("dashboard.general.last.month.top.downloaded.documents", "เอกสารที่มีผู้เปิดดูมากที่สุดในเดือนที่แล้ว");
	    lang.put("dashboard.general.last.week.top.modified.documents", "เอกสารที่ถูกแก้ไขบ่อยที่สุดในสัปดาห์ที่แล้ว");
	    lang.put("dashboard.general.last.month.top.modified.documents", "เอกสารที่ถูกแก้ไขบ่อยที่สุดในเดือนที่แล้ว");
	    lang.put("dashboard.general.last.uploaded.documents", "เอกสารที่ถูกอัพโหลดล่าสุด");
	    lang.put("dashboard.workflow.pending.tasks", "งานที่ค้าง");
	    lang.put("dashboard.workflow.pending.tasks.unassigned", "งานค้างที่ยังไม่ถูกมอบหมาย");
	    lang.put("dashboard.workflow.task", "ภาระงาน");
	    lang.put("dashboard.workflow.task.id", "รหัสงาน");
	    lang.put("dashboard.workflow.task.name", "ชื่อ");
	    lang.put("dashboard.workflow.task.created", "วันที่เปิดงาน");
	    lang.put("dashboard.workflow.task.start", "วันที่เริ่ม");
	    lang.put("dashboard.workflow.task.duedate", "วันกำหนดเสร็จ");
	    lang.put("dashboard.workflow.task.end", "วันสิ้นสุด");
	    lang.put("dashboard.workflow.task.description", "คำอธิบาย");
	    lang.put("dashboard.workflow.task.process.instance", "ลำดับขั้นตอน (process instance)");
	    lang.put("dashboard.workflow.task.process.id", "ID");
	    lang.put("dashboard.workflow.task.process.version", "เวอร์ชั่น");
	    lang.put("dashboard.workflow.task.process.name", "ชื่อ");
	    lang.put("dashboard.workflow.task.process.description", "คำอธิบาย");
	    lang.put("dashboard.workflow.task.process.data", "ขัอมูล");
	    lang.put("dashboard.workflow.comments", "ข้อคิดเห็น");
	    lang.put("dashboard.workflow.task.process.forms", "แบบฟอร์ม");
	    lang.put("dashboard.workflow.add.comment","เพิ่มข้อคิดเห็น");
	    lang.put("dashboard.workflow.task.process.definition", "นิยามของลำดับขั้นตอน (Process definition)");
	    lang.put("dashboard.workflow.task.process.path", "เส้นทาง");
	    lang.put("dashboard.refreshing", "กำลังรีเฟรซ");
	    lang.put("dashboard.keyword", "คำสืบค้น");
	    lang.put("dashboard.keyword.suggest", "ใส่คำที่ใช้สืบค้น");
	    lang.put("dashboard.keyword.all", "คำสืบค้นทั้งหมด");
	    lang.put("dashboard.keyword.top", "คำสืบค้นสูงสุด");
	    lang.put("dashboard.keyword.related", "คำสืบค้นที่เกี่ยวข้อง");
	    lang.put("dashboard.keyword.goto.document", "ไปยังเอกสาร");
	    lang.put("dashboard.keyword.clean.keywords", "ล้างคำสืบค้น");
	    lang.put("dashboard.mail.last.imported.mails", "อีเมล");
	    lang.put("dashboard.mail.last.imported.attached.documents", "เอกสารแนบ");
	    
	    // Workflow
	    lang.put("workflow.label", "เริ่มต้นโฟลว์งาน");
	    
	    // User configuration
	    lang.put("user.preferences.label", "การตั้งค่าผู้ใช้งาน");
	    lang.put("user.preferences.user", "ผู้ใช้งาน");
	    lang.put("user.preferences.password", "รหัสผ่าน");
	    lang.put("user.preferences.mail", "อีเมล");
	    lang.put("user.preferences.roles","บทบาท");
	    lang.put("user.preferences.imap.host", "เซิฟเวอร์ IMAP");
	    lang.put("user.preferences.imap.user", "ชื่อผู้ใช้งาน IMAP");
	    lang.put("user.preferences.imap.user.password", "รหัสผ่าน IMAP");
	    lang.put("user.preferences.imap.folder", "โฟลเดอร์ IMAP");
	    lang.put("user.preferences.password.error", "ผิดพลาด: รหัสผ่านไม่ถูกต้อง");
	    lang.put("user.preferences.user.data", "บัญชีผู้ใช้งาน");
	    lang.put("user.preferences.mail.data", "บัญชีอีเมล");
	    lang.put("user.preferences.imap.error", "ข้อมูลทุกช่องมีส่วนในการตั้งค่าใช้งานอีเมล");
	    lang.put("user.preferences.imap.password.error.void", "รหัสผ่านต้องไม่ถูกปล่อยว่างเมื่อสร้าง IMAP เมล");
	    lang.put("user.preferences.imap.test.error","การตั้งค่า IMAP ผิดพลาด");
	    lang.put("user.preferences.imap.test.ok","การตั้งค่า IMAP ใช้งานได้");

	    // Thesaurus
	    lang.put("thesaurus.directory.select.label", "เพื่มคำสืบค้นในพจนานุกรมคำพ้อง");
	    lang.put("thesaurus.tab.tree", "ลำดับชั้น");
	    lang.put("thesaurus.tab.keywords", "คำสืบค้น");
	    
	    // Categories
	    lang.put("categories.folder.select.label", "เพิ่มหมวดหมู่");
	    lang.put("categories.folder.error.delete", "ไม่สามารถลบหมวดหมู่ที่ยังมีเอกสารอยู่ได้");
	    
	    // Wizard
	    lang.put("wizard.document.uploading", "ต้วช่วยการอัพโหลดเอกสาร");
	    
	    // User info
	    lang.put("user.info.chat.connect", "ต่อเชื่อมกับแช็ต");
	    lang.put("user.info.chat.disconnect", "ตัดการเชื่อมกับแช็ต");
	    lang.put("user.info.chat.new.room", "เปิดห้องแช็ตใหม่");
	    lang.put("user.info.locked.actual", "เอกสารถูกล็อค");
	    lang.put("user.info.checkout.actual", "เช็คเอ้าต์เอกสาร");
	    lang.put("user.info.subscription.actual", "สมาชิกที่แท้จริง");
	    lang.put("user.info.news.new", "ข่าวสาร");
	    lang.put("user.info.workflow.pending", "โฟลว์งานที่ค้าง");
	    lang.put("user.info.user.quota", "โควต้าที่ใช้");
	    
	    // Users online popup
	    lang.put("user.online", "ผู้ใช้งานออนไลน์");
	    
	    // Chat room
	    lang.put("chat.room", "แช็ต");
	    lang.put("chat.users.in.room", "ผู้ใช้งาน");
	    
	    // Errors
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงเอกสารถูกปฏิเสธ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Lock, "ถูกปฏิเสธ - เอกสารล็อค");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Repository, "เกิดข้อพลาดภายในแหล่งบรรจุข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_PathNotFound, "หาเส้นทางเอกสารไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyService+ErrorCode.CAUSE_Version, "เวอร์ชั่นผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงเอกสารถูกปฏิเสธ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemNotFound, "หาเอกสารไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_ItemExists, "มีเอกสารอยู่แล้ว");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Lock, "ถูกปฏิเสธ - เอกสารล็อค");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_UnLock, "Document unlock desired");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งบรรจุข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในโปรแกรม");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_PathNotFound, "หาเส้นทางเอกสารไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDocumentService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงโฟลเดอร์ถูกปฏิเสธ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemNotFound, "ไม่พบโฟลเดอร์");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_ItemExists, "มีโฟลเดอร์อยู่แล้ว");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_PathNotFound, "หาเส้นทางโฟลเดอร์ไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMFolderService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");

	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงรายการถูกปฏิเสธ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_ItemNotFound, "หารายการไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMAuthService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_ItemNotFound, "หาเอกสารไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_UnsupportedMimeType, "ไม่รองรับไฟล์ฟอร์แม็ต");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_ItemExists, "มีเอกสารอยู่แล้ว");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_FileSizeExceeded, "ขนาดของเอกสารใหญ่เกินไป");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUploadService+ErrorCode.CAUSE_DocumentNameMismatch, "ชื่อเอกสารไม่ตรงกัน");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_Repository, "Session ถูกปิด");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDownloadService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_Repository, "Generic error executing query");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_ItemExists, "วิธีการสืบค้นที่จะเก็บไว้ต้องใช้ชื่อไม่ซ้ำกัน");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMSearchService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBookmarkService+ErrorCode.CAUSE_ItemExists, "ชื่อ bookmark ต้องไม่ซ้ำกัน");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRemoteService+ErrorCode.CAUSE_SessionLost , "Session หาย");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMDashboardService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkspaceService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMWorkflowService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_Configuration, "Error in browser configuration");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMBrowser+ErrorCode.CAUSE_QuotaExceed, "ใช้โควต้าเกิน!! กรุณาติดต่อผู้ดูแลระบบ");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงเอกสารถูกปฏิเสธ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemNotFound, "หาเอกสารไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_ItemExists, "มีเอกสารอยู่แล้ว");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Lock, "ถูกปฏิเสธ - เอกสารล็อค");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในโปรแกรม");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_PathNotFound, "หาเส้นเอกสารไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMMailService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_PathNotFound, "หาเส้นทางไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงถูกปฏิเสธ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMNotifyService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในโปรแกรม");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Lock, "ถูกล็อค");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_PathNotFound, "หาเส้นทางไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงถูกปฏิเสธ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งบรรจุข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในโปรแกรม");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchGroup, "ไม่มีกลุ่ม");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_NoSuchProperty, "Property not exists");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMPropertyGroupService+ErrorCode.CAUSE_IOException, "Error on I/O");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดภายในแหล่งเก็บข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในโปรแกรม");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_PathNotFound, "หาเส้นทางไม่พบ");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMRepositoryService+ErrorCode.CAUSE_AccessDenied, "การเข้าถึงถูกปฎิเสธ");
	    
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_Repository, "เกิดข้อผิดพลาดจากภายในแหล่งบรรจุข้อมูล");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_General, "เกิดข้อผิดพลาดภายในโปรแกรม");
	    lang.put("OKM-"+ErrorCode.ORIGIN_OKMUserCopyService+ErrorCode.CAUSE_DatabaseException, "ฐานข้อมูลผิดพลาด");
	  }
}
