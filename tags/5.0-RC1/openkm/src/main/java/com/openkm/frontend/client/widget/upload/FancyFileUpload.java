package com.openkm.frontend.client.widget.upload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.widgetideas.client.ProgressBar;
import com.google.gwt.widgetideas.client.ProgressBar.TextFormatter;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTFileUploadingStatus;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.config.ErrorCode;
import com.openkm.frontend.client.panel.PanelDefinition;
import com.openkm.frontend.client.service.OKMGeneralService;
import com.openkm.frontend.client.service.OKMGeneralServiceAsync;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.notify.NotifyPanel;

/**
 * FancyFileUpload
 * 
 * @author jllort
 *
 */
public class FancyFileUpload extends Composite implements HasText, HasChangeHandlers {
	
	private final OKMGeneralServiceAsync generalService = (OKMGeneralServiceAsync) GWT.create(OKMGeneralService.class);
	
	// Upload actions
	public static final int ACTION_NONE   = -1;
	public static final int ACTION_INSERT = 0;
	public static final int ACTION_UPDATE = 1;
	public static final int ACTION_FOLDER = 2;
	
	/**
	 * State definitions
	 */
	public static final int EMPTY_STATE = 1;
	public static final int PENDING_STATE = 2;
	public static final int UPLOADING_STATE = 3;
	public static final int UPLOADED_STATE = 4;
	public static final int FAILED_STATE = 5;
	
	/**
	 * OK message expected from file upload servlet to indicate successful
	 * upload.
	 */
	private static final String returnOKMessage = "OKM_OK";
	private static final String returnErrorMessage = "OKM-";

	/**
	 * Initial State of the widget.
	 */
	private int widgetState = EMPTY_STATE;

	/**
	 * Default delay for pending state, when delay over the form is submitted.
	 */
	private int pendingUpdateDelay = 1000;
	
	private TextBox inputPath = new TextBox();
	private TextBox inputAction = new TextBox();
	private FormPanel uploadForm = new FormPanel();
	private Button send = new Button();
	private VerticalPanel mainPanel = new VerticalPanel();
	private CheckBox notifyToUser = new CheckBox();
	private CheckBox importZip = new CheckBox();
	private HTML versionCommentText = new HTML();
	private HTML notifyToUserText = new HTML();
	private HTML importZipText = new HTML();
	private HorizontalPanel hNotifyPanel = new HorizontalPanel();
	private HorizontalPanel hUnzipPanel = new HorizontalPanel();
	private NotifyPanel notifyPanel = new NotifyPanel();
	private HTML versionHTMLBR;
	private TextArea versionComment;
	private ScrollPanel versionCommentScrollPanel;
	private TextBox users;
	private TextBox roles;
	private TextArea message;
	private VerticalPanel vNotifyPanel = new VerticalPanel();
	private HTML commentTXT;
	private ScrollPanel messageScroll;
	private HTML errorNotify;
	private ProgressBar progressBar;
	private TextFormatter progressiveFormater;
	private TextFormatter finalFormater;
	private boolean wizard = false;
	private int action = ACTION_NONE;

	/**
	 * Internal timer for checking if pending delay is over.
	 */
	private Timer p;

	/**
	 * Widget representing file to be uploaded.
	 */
	private UploadDisplay uploadItem;

	/**
	 * FileName to be uploaded
	 */
	String fileName = "";

	/**
	 * Uploading status
	 */
	private GWTFileUploadingStatus fileUploadingStatus = new GWTFileUploadingStatus();
	private boolean fileUplodingStartedFlag = false;
	
	/**
	 * Class used for the display of filename to be uploaded, and handling the
	 * update of the display states.
	 * 
	 */
	protected class UploadDisplay extends Composite {

		/**
		 * FileUpload Widget
		 */
		FileUpload uploadFileWidget = new FileUpload();

		/**
		 * Label to display after file widget is filled with a filename
		 */
		HTML status = new HTML();
		
		/**
		 * Label to display if some error on unzip uplaoded file
		 */
		HTML statusZipNotify;
		ScrollPanel statusZipNotifyScroll;

		/**
		 * Panel to hold the widget
		 */
		FlowPanel mainPanel = new FlowPanel();

		/**
		 * Panel to hold pending, loading, loaded or failed state details.
		 */
		VerticalPanel pendingPanel = new VerticalPanel();
		
		HorizontalPanel hFileUpload = new HorizontalPanel();

		/**
		 * Constructor
		 * 
		 */
		public UploadDisplay() {
			uploadFileWidget.setStyleName("okm-Input");
			
			send.setText(Main.i18n("fileupload.send"));
			send.setStyleName("okm-Button");
			// Set up a click listener on the proceed check box
			send.addClickHandler(new ClickHandler() { 
				@Override
				public void onClick(ClickEvent event) {
					if (Main.get().mainPanel.bottomPanel.userInfo.isQuotaExceed()) {
						Main.get().showError("UserQuotaExceed", 
					             			 new OKMException("OKM-"+ErrorCode.ORIGIN_OKMBrowser + ErrorCode.CAUSE_QuotaExceed, ""));
					} else {
						users.setText(notifyPanel.getUsersToNotify());
						roles.setText(notifyPanel.getRolesToNotify());
						if (notifyToUser.getValue() && users.getText().equals("") && roles.getText().equals("")) {
							errorNotify.setVisible(true);
						} else if (uploadFileWidget.getFilename() != null && !uploadFileWidget.getFilename().equals("")) {
							pendingUpload();
						}
					}
				}
			});
			
			hFileUpload.add(uploadFileWidget);
			hFileUpload.add(new HTML("&nbsp;&nbsp;"));
			hFileUpload.add(send);
			hFileUpload.setWidth("250");
			
			status.setWidth("100%");
			status.setWordWrap(true);
			status.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
			
			// Adds error panel when zip file is uploaded
			statusZipNotify = new HTML();
			statusZipNotify.setSize("100%","100%");
			statusZipNotify.setVisible(true);
			statusZipNotifyScroll = new ScrollPanel(statusZipNotify);
			statusZipNotifyScroll.setAlwaysShowScrollBars(false);
			statusZipNotifyScroll.setVisible(false);
			statusZipNotifyScroll.setSize("280","100");
			statusZipNotifyScroll.setStyleName("okm-Bookmark-Panel");
			statusZipNotifyScroll.addStyleName("okm-Input");
			
			progressiveFormater = new TextFormatter() {
				@Override
				protected String getText(ProgressBar bar, double curProgress) {
					String text = "";
					text += Util.formatSize(curProgress);
					text += " " + Main.i18n("fileupload.status.of") + " ";
					text += Util.formatSize(progressBar.getMaxProgress());
					text += " " +(int) (100 * progressBar.getPercent()) + "% "; 
					return text;
				}
			};
			
			finalFormater = new TextFormatter() {
				@Override
				protected String getText(ProgressBar bar, double curProgress) {
					String text = " " +(int) (100 * progressBar.getPercent()) + "% "; 
					return text;
				}
			};
			
			progressBar = new ProgressBar();
			progressBar.setTextFormatter(progressiveFormater);
			
			HorizontalPanel hPBPanel = new HorizontalPanel();
			hPBPanel.add(progressBar); 
			hPBPanel.setCellVerticalAlignment(progressBar, HasAlignment.ALIGN_MIDDLE);
			hPBPanel.setCellHorizontalAlignment(progressBar, HasAlignment.ALIGN_LEFT); // Corrects some problem with centering progress status 
			progressBar.setSize("260", "20"); 
			
			pendingPanel.setWidth("280");
			pendingPanel.setVisible(true);
			pendingPanel.add(status);
			pendingPanel.add(hPBPanel);
			
			pendingPanel.setCellHorizontalAlignment(hPBPanel, HasAlignment.ALIGN_CENTER);
			
			mainPanel.add(hFileUpload);
			mainPanel.add(pendingPanel);
			mainPanel.add(statusZipNotifyScroll);
			
			initWidget(mainPanel);
		}

		/**
		 * Set the widget into pending mode by altering style of pending panel
		 * and displaying it. Hide the FileUpload widget and finally set the
		 * state to Pending.
		 * 
		 */
		private void setPending() {
			status.setHTML(Main.i18n("fileupload.status.sending"));
			pendingPanel.setStyleName("fancyfileupload-pending");
			widgetState = PENDING_STATE;
			fireChange();
		}

		/**
		 * Set the widget into Loading mode by changing the style name and
		 * updating the widget State to Uploading.
		 * 
		 */
		public void setLoading() {
			pendingPanel.setStyleName("fancyfileupload-loading");
			hFileUpload.setVisible(false);
			pendingPanel.setVisible(true);
			widgetState = UPLOADING_STATE;
			fileUplodingStartedFlag = true; // Activates flash uploading is started
			getFileUploadStatus();
			fireChange();
		}
		
		/**
		 * Set the widget into pending mode by altering style of pending panel
		 * and displaying it. Hide the FileUpload widget and finally set the
		 * state to Pending.
		 */
		private void setIndexing() {
			status.setHTML(Main.i18n("fileupload.status.indexing"));
		}

		/**
		 * Set the widget to Loaded mode by changing the style name and updating
		 * the widget State to Loaded.
		 * 
		 */
		private void setLoaded() {
			// Sometimes if upload is fast, has no time to getting file uploading status information
			// on this cases must be setting it directly ( simulating )
			if (fileUploadingStatus.getContentLength()==0) {
				progressBar.setTextFormatter(finalFormater);
				progressBar.setMaxProgress(100);
				progressBar.setProgress(100);
			}
			pendingPanel.setStyleName("fancyfileupload-loaded");
			status.setHTML(Main.i18n("fileupload.status.ok"));
			widgetState = UPLOADED_STATE;
			fileUplodingStartedFlag = false;
			if (!wizard) {
				refresh();
			}
			fireChange();
			Main.get().mainPanel.dashboard.userDashboard.getUserLastModifiedDocuments();
			Main.get().mainPanel.dashboard.userDashboard.getUserCheckedOutDocuments();
			Main.get().mainPanel.dashboard.userDashboard.getUserLastUploadedDocuments();
			Main.get().workspaceUserProperties.getUserDocumentsSize();
		}

		/**
		 * Set the widget to Failed mode by changing the style name and updating
		 * the widget State to Failed. Additionally, hide the pending panel and
		 * display the FileUpload widget.
		 * 
		 */
		private void setFailed(String msg) {
			// Sometimes if upload is fast, has no time to getting file uploading status information
			// on this cases must be setting it directly ( simulating )
			if (fileUploadingStatus.getContentLength()==0) {
				progressBar.setTextFormatter(finalFormater);
				progressBar.setMaxProgress(100);
				progressBar.setProgress(100);
			}
			
			if (importZip.getValue()) {
				statusZipNotify.setHTML(msg.replaceAll("\n", "<br/>"));
				statusZipNotifyScroll.setVisible(true);
				pendingPanel.setVisible(true);
				status.setText(Main.i18n("fileupload.label.error.importing.zip"));
			} else if (msg.contains(returnErrorMessage)) {
				status.setHTML(Main.i18n(msg.substring(msg.indexOf("OKM"), msg.indexOf("OKM") + 10)));
			} else {
				status.setHTML(msg);
			}
			
			pendingPanel.setStyleName("fancyfileupload-failed");
			widgetState = FAILED_STATE;
			fileUplodingStartedFlag = false;
			refresh();
			fireChange();
		}

		/**
		 * Reset the display
		 * 
		 */
		private void reset(boolean enableImport) {
			widgetState = EMPTY_STATE;
			fireChange();
			
			// Reseting values
			fileName = "";			
			status.setText("");
			statusZipNotify.setText("");
			statusZipNotifyScroll.setVisible(false);
			message.setText("");
			versionComment.setText("");
			users.setText("");
			roles.setText("");
			notifyPanel.reset();
			getAllUsers();
			
			// On on root stack panel enabled must be enabled notify to user option
			if (Main.get().mainPanel.desktop.navigator.getStackIndex() != PanelDefinition.NAVIGATOR_TAXONOMY) {
				hNotifyPanel.setVisible(false);
			} else {
				hNotifyPanel.setVisible(true);
			}
			
			errorNotify.setVisible(false);
			vNotifyPanel.setVisible(false);
			notifyToUser.setValue(false);
			importZip.setValue(false);
			hFileUpload.setVisible(true);
			pendingPanel.setVisible(false);
			
			if (enableImport) {
				hUnzipPanel.setVisible(true);
			} else {
				hUnzipPanel.setVisible(false);
			}
			
			resetProgressBar();
		}
		
		/**
		 * Inits values before reset ( used to correct center panel )
		 */
		private void init() {
			vNotifyPanel.setVisible(true);
		}
	}
	
	/**
	 * Refresh folders and documents
	 */
	public void refresh() {
		if (importZip.getValue()) {
			Main.get().activeFolderTree.refresh(true);
		} else {
			Main.get().mainPanel.desktop.browser.fileBrowser.refresh(Main.get().activeFolderTree.getActualPath());
		}
	}

	/**
	 * Perform the uploading of a file by changing state of display widget and
	 * then calling form.submit() method.
	 * 
	 */
	private void uploadFiles() {
		fileName = uploadItem.uploadFileWidget.getFilename();
		uploadItem.setLoading();
		uploadForm.submit();
	}

	/**
	 * Put the widget into a Pending state, set the Pending delay timer to call
	 * the upload file method when ran out.
	 * 
	 */
	private void pendingUpload() {
		// Fire an onChange event to anyone who is listening
		uploadItem.setPending();
		p = new Timer() {
			public void run() {
				uploadFiles();
			}
		};
		p.schedule(pendingUpdateDelay);
	}

	/**
	 * FancyFileUpload.
	 * 
	 */
	public FancyFileUpload() {
		inputPath.setName("path");
		inputPath.setVisible(false);
		mainPanel.add(inputPath);

		inputAction.setName("action");
		inputAction.setVisible(false);
		mainPanel.add(inputAction);
		
		// Set Form details
		// Set the action to call on submit
		uploadForm.setAction(Config.OKMFileUploadService);
		// Set the form encoding to multipart to indicate a file upload
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		// Set the method to Post
		uploadForm.setMethod(FormPanel.METHOD_POST);
		uploadForm.setWidget(mainPanel);
		
		// Create a new upload display widget
		uploadItem = new UploadDisplay();
		// Set the name of the upload file form element
		uploadItem.uploadFileWidget.setName("uploadFormElement");
		// Add the new widget to the panel.
		mainPanel.add(uploadItem);
		
		// Adds error panel, whem user select notify but not select any user
		errorNotify = new HTML(Main.i18n("fileupload.label.must.select.users"));
		errorNotify.setWidth("270");
		errorNotify.setVisible(false);
		errorNotify.setStyleName("fancyfileupload-failed");
		mainPanel.add(errorNotify);
		
		// Adds version comment
		versionHTMLBR = new HTML("<br>");
		mainPanel.add(versionHTMLBR);
		versionComment = new TextArea();
		versionComment.setWidth("280");
		versionComment.setHeight("50");
		versionComment.setName("comment");
		versionComment.setStyleName("okm-Input");
		versionCommentText = new HTML(Main.i18n("fileupload.label.comment"));
		// TODO This is a workaround for a Firefox 2 bug
		// http://code.google.com/p/google-web-toolkit/issues/detail?id=891
		// Table for solve some visualization problems
		versionCommentScrollPanel = new ScrollPanel(versionComment);
		versionCommentScrollPanel.setAlwaysShowScrollBars(false);
		versionCommentScrollPanel.setSize("100%","100%");
		mainPanel.add(versionCommentText);
		mainPanel.add(versionCommentScrollPanel);
		
		// Ads unzip file
		importZip = new CheckBox();
		importZip.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
					if (importZip.getValue()) {
						notifyToUser.setValue(false);
						vNotifyPanel.setVisible(false);
					} 
				}
			}
		);
		importZip.setName("importZip");
		importZipText = new HTML(Main.i18n("fileupload.label.importZip"));
		hUnzipPanel = new HorizontalPanel();
		hUnzipPanel.add(importZip);
		hUnzipPanel.add(importZipText);
		hUnzipPanel.setCellVerticalAlignment(importZip, VerticalPanel.ALIGN_MIDDLE);
		hUnzipPanel.setCellVerticalAlignment(importZipText, VerticalPanel.ALIGN_MIDDLE);
		mainPanel.add(new HTML("<br>"));
		mainPanel.add(hUnzipPanel);
		
		// Adds the notify checkbox
		users = new TextBox();
		users.setName("users");
		users.setVisible(false);
		roles = new TextBox();
		roles.setName("roles");
		roles.setVisible(false);
		notifyToUser = new CheckBox();
		notifyToUser.addClickHandler(new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
					if (notifyToUser.getValue()) {
						vNotifyPanel.setVisible(true);
						importZip.setValue(false);
					} else {
						errorNotify.setVisible(false);
						vNotifyPanel.setVisible(false);
					}
				}
			}
		);
		notifyToUser.setName("notify");
		notifyToUserText = new HTML(Main.i18n("fileupload.label.users.notify"));
		hNotifyPanel = new HorizontalPanel();
		hNotifyPanel.add(notifyToUser);
		hNotifyPanel.add(notifyToUserText);
		hNotifyPanel.setCellVerticalAlignment(notifyToUser, VerticalPanel.ALIGN_MIDDLE);
		hNotifyPanel.setCellVerticalAlignment(notifyToUserText, VerticalPanel.ALIGN_MIDDLE);
		mainPanel.add(hNotifyPanel);	
		mainPanel.add(new HTML("<br>"));
		
		// The notify user tables
		message = new TextArea();
		commentTXT = new HTML(Main.i18n("fileupload.label.notify.comment"));
		message.setName("message");
		message.setSize("280","60");
		message.setStyleName("okm-Input");
		
		vNotifyPanel = new VerticalPanel();
		vNotifyPanel.add(commentTXT);
		// TODO This is a workaround for a Firefox 2 bug
		// http://code.google.com/p/google-web-toolkit/issues/detail?id=891
		messageScroll = new ScrollPanel(message);
		messageScroll.setAlwaysShowScrollBars(false);

		vNotifyPanel.add(messageScroll);
		vNotifyPanel.add(new HTML("<br>"));
		vNotifyPanel.add(notifyPanel);
		vNotifyPanel.add(new HTML("<br>"));
		
		mainPanel.add(users);
		mainPanel.add(roles);
		mainPanel.add(vNotifyPanel);
		
		// Set align to panels
		mainPanel.setCellHorizontalAlignment(hNotifyPanel,HorizontalPanel.ALIGN_LEFT);
		mainPanel.setCellHorizontalAlignment(hUnzipPanel,HorizontalPanel.ALIGN_LEFT);
		
		// Initialices users
		getAllUsers();
		
		// Initialise the widget.
		initWidget(uploadForm);

		// Add an event handler to the form.
		uploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// Fire an onChange Event
				fireChange();
				// Cancel all timers to be absolutely sure nothing is going on.
				p.cancel();
				// Ensure that the form encoding is set correctly.
				uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
				// Check the result to see if an OK message is returned from the
				// server.
				
				// Return params could be <pre> or <pre style=""> with some IE and chrome
				String msg = event.getResults();
				
				if (msg.contains(returnOKMessage)) {
					String docPath = "";
					if (msg.indexOf("path[")>0 && msg.indexOf("]path")>0) {
						docPath = msg.substring(msg.indexOf("path[")+5,msg.indexOf("]path"));
					}
					
					// Case is not importing a zip and wizard is enabled
					if (!importZip.getValue() && action== ACTION_INSERT &&
						(Main.get().workspaceUserProperties.getWorkspace().isWizardPropertyGroups() || 
						 Main.get().workspaceUserProperties.getWorkspace().isWizardCategories() ||
						 Main.get().workspaceUserProperties.getWorkspace().isWizardKeywords())) {
						
						Main.get().wizardPopup.start(docPath);
						wizard = true;
					} else {
						wizard = false;
					}
					
					// By default selected row after uploading is uploaded file
					if (!docPath.equals("")) {
						Main.get().mainPanel.desktop.browser.fileBrowser.mantainSelectedRowByPath(docPath);
					}
					
					uploadItem.setLoaded();
				} else {
					uploadItem.setFailed(msg);
				}
			}
		});
	}

	/**
	 * Reset he upload
	 */
	public void reset(boolean enableImport) {
		uploadItem.reset(enableImport);
	}
	
	/**
	 * Init he upload
	 */
	public void init() {
		uploadItem.init();
	}
	
	/**
	 * Get the text from the widget - which in reality will be retrieving any
	 * value set in the Label element of the display widget.
	 */
	public String getText() {
		return uploadItem.status.getText();
	}

	/**
	 * Cannot set the text of a File Upload Widget, so raise an exception.
	 */
	public void setText(String text) {
		throw new RuntimeException("Cannot set text of a FileUpload Widget");
	}

	/**
	 * Retrieve the status of the upload widget.
	 * 
	 * @return Status of upload widget.
	 */
	public int getUploadState() {
		return widgetState;
	}
	
	/**
	 * isWizard
	 * 
	 * @return
	 */
	public boolean isWizard() {
		return wizard;
	}

	/**
	 * Set the delay value indicating how long a file will remain in pending
	 * mode prior to the upload action taking place.
	 * 
	 * @param newDelay
	 */
	public void setPendingDelay(int newDelay) {
		pendingUpdateDelay = newDelay;
	}

	/**
	 * Return value set for pending delay.
	 * 
	 * @return
	 */
	public int getPendingDelay() {
		return pendingUpdateDelay;
	}
	
	/**
	 * fire a change event
	 */
	private void fireChange() {
		 NativeEvent nativeEvent = Document.get().createChangeEvent();
		 ChangeEvent.fireNativeEvent(nativeEvent, this);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasChangeHandlers#addChangeHandler(com.google.gwt.event.dom.client.ChangeHandler)
	 */
	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	public void setAction(int action) {
		this.action = action;
		switch (action) {
			case ACTION_INSERT :
				versionComment.setVisible(false);
				versionCommentText.setVisible(false);
				versionHTMLBR.setVisible(false);
				break;
				
			case ACTION_UPDATE:
				versionComment.setVisible(true);
				versionCommentText.setVisible(true);
				versionHTMLBR.setVisible(true);
				break;
		}
		inputAction.setText(""+action);
	}

	/**
	 * Set the paht
	 * @param path String path
	 */
	public void setPath(String path) {
		inputPath.setText(path);
	}

	/**
	 * Refreshing language
	 */
	public void langRefresh() {
		send.setText(Main.i18n("fileupload.send"));	
		notifyToUserText.setHTML(Main.i18n("fileupload.label.users.notify"));
		importZipText.setHTML(Main.i18n("fileupload.label.importZip"));
		versionCommentText.setHTML(Main.i18n("fileupload.label.comment"));
		commentTXT.setHTML(Main.i18n("fileupload.label.notify.comment"));
		notifyPanel.langRefresh();
	}
	
	/**
	 * Call back get file upload status
	 */
	final AsyncCallback<GWTFileUploadingStatus> callbackGetFileUploadStatus = new AsyncCallback<GWTFileUploadingStatus>() {
		public void onSuccess(GWTFileUploadingStatus result) {
			fileUploadingStatus = result;
			
			if (fileUplodingStartedFlag) {
				if (result.isStarted()) {
					if (result.getContentLength()!=0 && result.getContentLength()==result.getBytesRead()) {
						result.setUploadFinish(true);
						uploadItem.setIndexing();
					}
					
					if (result.isUploadFinish()) {
						progressBar.setTextFormatter(finalFormater);
					}
					
					progressBar.setMaxProgress(fileUploadingStatus.getContentLength());
					progressBar.setProgress(fileUploadingStatus.getBytesRead());
				} 

				if (!result.isUploadFinish()) {
					getFileUploadStatus();
				}
			}
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getFileUploadStatus", caught);
		}
	};
	
	/**
	 * Resets the progress bar and all related values
	 */
	private void resetProgressBar() {
		fileUplodingStartedFlag = false;
		fileUploadingStatus = new GWTFileUploadingStatus();
		progressBar.setMinProgress(0);
		progressBar.setMaxProgress(0);
		progressBar.setProgress(0);
		progressBar.setTextFormatter(progressiveFormater);
	}
	
	/**
	 * Gets all users
	 */
	private void getAllUsers() {
		notifyPanel.getAll();
	}
	
	private void getFileUploadStatus() {
		ServiceDefTarget endPoint = (ServiceDefTarget) generalService;
		endPoint.setServiceEntryPoint(Config.OKMGeneralService);	
		generalService.getFileUploadStatus(callbackGetFileUploadStatus);
	}
	
	/**
	 * disableErrorNotify
	 */
	public void disableErrorNotify() {
		errorNotify.setVisible(false); 
	}
	
	/**
	 * enableAdvancedFilter
	 */
	public void enableAdvancedFilter() {
		notifyPanel.enableAdvancedFilter();
	}
}