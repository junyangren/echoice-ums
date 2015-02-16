Ext.namespace('Ext.ux.UploadDialog');

/**
 * Record type for dialogs grid.
 */
Ext.ux.UploadDialog.FileRecord = Ext.data.Record.create([
  {name: 'filename'},
  {name: 'state', type: 'int'},
  {name: 'note'},
  {name: 'input_element'}
]);

Ext.ux.UploadDialog.FileRecord.STATE_QUEUE = 0;
Ext.ux.UploadDialog.FileRecord.STATE_FINISHED = 1;
Ext.ux.UploadDialog.FileRecord.STATE_FAILED = 2;
Ext.ux.UploadDialog.FileRecord.STATE_PROCESSING = 3;

/**
 *  Upload file toolbar button
 */
Ext.ux.UploadDialog.UploadButton = function(config)
{
  this.original_handler = config.handler || null;
  this.original_scope = config.scope || null;
   
  Ext.ux.UploadDialog.UploadButton.superclass.constructor.call(this, config);
}

Ext.extend(Ext.ux.UploadDialog.UploadButton, Ext.Toolbar.Button, {

  original_handler : null,
  
  original_scope : null,
  
  input_file : null,
  
  input_name : 'file',
  
  button_box : null,
  
  render : function(td)
  {
    Ext.ux.UploadDialog.UploadButton.superclass.render.call(this, td);
    var td = Ext.get(td);
    td.position('relative');
    this.button_box = td.getBox(false, true);
    td.setStyle('overflow', 'hidden');
    
    this.detachInputFile();
  },
  
  detachInputFile : function()
  {
    var prev_input_file =this.input_file;
    
    // Detaching the previous one
    if (this.input_file) {
      if (typeof this.tooltip == 'object') {
        Ext.QuickTips.unregister(this.input_file);
      }
      else {
        this.input_file.dom[this.tooltipType] = null;
      }
      this.input_file.removeAllListeners();
      this.input_file = null;
    }
    
    // Creating new input type='file'
    this.input_file = Ext.DomHelper.append(
      this.td, 
      {
        tag: 'input',
        type: 'file',
        size: 1,
        name: this.input_name,
        style: 'position: absolute; cursor: pointer'
      },
      true
    );    
    this.input_file.applyStyles({
      'font-size': (this.button_box.width * 0.5) + 'px',
	    border: 'none'
    });
    
    var input_box = this.input_file.getBox();
    var adj = {x: 3, y: 3}
    if (Ext.isIE) {
      adj = {x: 0, y: 3}
    }
    this.input_file.setLeft(this.button_box.width - input_box.width + adj.x + 'px');
    this.input_file.setTop(this.button_box.height - input_box.height + adj.y + 'px');    
    this.input_file.setOpacity(0.0);
    
    this.input_file.on('change', this.onInputFileChange, this);
    // Hover effect
    if (this.handleMouseEvents) {
      this.input_file.on('mouseover', this.onMouseOver, this);
      this.input_file.on('mouseout', this.onMouseOut, this);
      this.input_file.on('mousedown', this.onMouseDown, this);
    }
    // Quick tips
    if (this.tooltip) {
      if(typeof this.tooltip == 'object'){
        Ext.QuickTips.tips(Ext.apply({
          target: this.input_file
        }, this.tooltip));
      } else {
        this.input_file.dom[this.tooltipType] = this.tooltip;
      }
    }
    // returning previous input
    return prev_input_file;
  },
  
  getInputFile : function()
  {
    return this.input_file;
  },
  
  destroy : function()
  {
    this.detachInputFile();
    this.input_file.remove();
    this.input_file = null;
    Ext.ux.UploadDialog.UploadButton.superclass.destroy.call(this);      
  },
  
  disable : function()
  {
    Ext.ux.UploadDialog.UploadButton.superclass.disable.call(this);  
    this.disableInputFile();
  },
  
  disableInputFile : function()
  {
    if (this.input_file) {
      this.input_file.dom.disabled = true;
    }
  },
  
  enable : function()
  {
    Ext.ux.UploadDialog.UploadButton.superclass.enable.call(this);
    this.enableInputFile();
  },
  
  enableInputFile : function()
  {
    if (this.input_file) {
      this.input_file.dom.disabled = false;
    }
  },
  
  onInputFileChange : function()
  {
    if (this.original_handler) {
      this.original_handler.call(this.original_scope || window, this);
    }
  }
});


Ext.ux.UploadDialog.Dialog = function(el, config)
{
  Ext.ux.UploadDialog.Dialog.superclass.constructor.call(this, el, config);
  
  this.addEvents({
    'filetest': true,
    'fileadd' : true,
    'fileremove' : true,
    'resetqueue' : true,
    'uploadsuccess' : true,
    'uploaderror' : true,
    'uploadfailed' : true,
    'uploadstart' : true,
    'uploadstop' : true,
    'uploadcomplete' : true
  });
  
  this.state_tpl = new Ext.Template(
    "<div class='x-ux-uploaddialog-state x-ux-uploaddialog-state-{state}'>&#160;</div>"
  ).compile();
  
  this.createForm();
  this.createGrid();
  
  this.on('resize', this.onDialogResize, this);
  this.on('beforehide', this.onDialogBeforeHide, this);
  this.on('hide', this.onDialogHide, this);
}

Ext.extend(Ext.ux.UploadDialog.Dialog, Ext.BasicDialog, {

  form : null,

  grid : null,
  
  store : null,
  
  toolbar : null,
  
  progress : null,
  
  state_tpl : null,
  
  body_delta : { width: 0, height: 0 },

  reset_on_hide : true,  
  
  url : '',
  
  base_params : null,
  
  permitted_extensions : [],
  
  allow_close_on_upload : false,
  
  upload_autostart : false,
  
  is_uploading : false,
  
  initial_queued_count : 0,
  
  createForm : function(el)
  {
    var el = el || this.body;
    this.form = Ext.DomHelper.append(el, {
      tag : 'form',
      'class' : 'x-ux-uploaddialog-form',
      method : 'post',
      enctype : 'multipart/form-data',
      action : this.url      
    }, true);
  },
  
  createGrid : function(el)
  { 
    this.store = new Ext.data.Store({
      proxy: new Ext.data.MemoryProxy([]),
      reader: new Ext.data.JsonReader({}, Ext.ux.UploadDialog.FileRecord),
      sortInfo: {field: 'state', direction: 'DESC' },
      pruneModifiedRecords: true
    });
    
    var cm = new Ext.grid.ColumnModel([
      {
        id: 'state',
        header: this.i18n.state_col_title,
        width: this.i18n.state_col_width,
        resizable: false,
        fixed: true,
        dataIndex: 'state',
        sortable: true,
        renderer: this.renderStateCell.createDelegate(this)
      },
      {
        id: 'filename',
        header: this.i18n.filename_col_title,
        dataIndex: 'filename',
        sortable: true,
        renderer: this.renderFilenameCell.createDelegate(this)
      },
      {
        id: 'note',
        header: this.i18n.note_col_title,
        width: this.i18n.note_col_width, 
        resizable: false,
        fixed: true,       
        dataIndex: 'note',
        sortable: true,
        renderer: this.renderNoteCell.createDelegate(this)
      }
    ]);
    
    this.grid = new Ext.grid.Grid(
      el || this.form,
      {
        ds: this.store,
        cm: cm,
        sm: new Ext.grid.RowSelectionModel(),
        autoExpandColumn: 'filename',
        trackMouseOver: true,
        monitorWindowResize: false,
        autoHeight: false,
        autoWidth: false
      }
    );
    this.grid.getGridEl().replaceClass("x-layout-inactive-content", "x-layout-component-panel");
    
    this.store.load();
    this.grid.on('render', this.onGridRender, this);
    this.grid.getSelectionModel().on('selectionchange', this.updateToolbar, this);
    this.grid.render();
  },
  
  createGridToolbar : function(el)
  {
    el = el || this.grid.getView().getFooterPanel(true);
    
    this.toolbar = new Ext.Toolbar(el);
    this.toolbar.x_buttons = {};
    this.toolbar.x_buttons.add = this.toolbar.addItem(new Ext.ux.UploadDialog.UploadButton({
      text: this.i18n.add_btn_text,
      tooltip: this.i18n.add_btn_tip,
      iconCls: 'x-ux-uploaddialog-addbtn',
      cls: 'x-btn-text-icon',
      handler: this.onAddButtonFileSelected,
      scope: this
    }));
    this.toolbar.x_buttons.remove = this.toolbar.addItem(new Ext.Toolbar.Button({
      text: this.i18n.remove_btn_text,
      tooltip: this.i18n.remove_btn_tip,
      iconCls: 'x-ux-uploaddialog-removebtn',
      cls: 'x-btn-text-icon',
      handler: this.onRemoveButtonClick,
      scope: this
    }));
    this.toolbar.x_buttons.reset = this.toolbar.addItem(new Ext.Toolbar.Button({
      text: this.i18n.reset_btn_text,
      tooltip: this.i18n.reset_btn_tip,
      iconCls: 'x-ux-uploaddialog-resetbtn',
      cls: 'x-btn-text-icon',
      handler: this.onResetButtonClick,
      scope: this
    }));
    this.toolbar.add('-');
    this.toolbar.x_buttons.upload = this.toolbar.addItem(new Ext.Toolbar.Button({
      text: this.i18n.upload_btn_start_text,
      tooltip: this.i18n.upload_btn_tip,
      iconCls: 'x-ux-uploaddialog-uploadstartbtn',
      cls: 'x-btn-text-icon',
      handler: this.onUploadButtonClick,
      scope: this
    }));
    this.toolbar.add('-');
    this.toolbar.x_buttons.indicator = this.toolbar.addItem(
      new Ext.Toolbar.Item(
        Ext.DomHelper.append(this.form, {
          tag: 'div',
          'class': 'x-ux-uploaddialog-indicator-stoped',
          html: '&#160'
        })
      )
    );
    this.toolbar.add('->');
    this.toolbar.x_buttons.close = this.toolbar.addItem(new Ext.Toolbar.Button({
      text: this.i18n.close_btn_text,
      tooltip: this.i18n.close_btn_tip,
      handler: this.onCloseButtonClick,
      scope: this
    }));
  },
  
  createProgressBar : function(el)
  {
    el = el || this.grid.getView().getHeaderPanel(true);
    var tb_body = Ext.DomHelper.append(
      el, 
      {
        tag: 'div', 
        'class': 'x-toolbar'
      }
    );
    this.progress = new Ext.ux.ProgressBar({
      renderTo: tb_body,
      value: 0,
      text: this.i18n.progress_waiting_text,
      style: 'width: auto'
    });
  },
  
  show: function(anim_target)
  {
    Ext.ux.UploadDialog.Dialog.superclass.show.call(this, anim_target);
    var bs = Ext.fly(this.body).getSize(true);
    var ds = Ext.fly(this.getEl()).getSize(true);
    this.body_delta = {
      width: ds.width - bs.width,
      height: ds.height - bs.height
    }
    this.onDialogResize(this, ds.width, ds.height);
  },
  
  renderStateCell: function(data, cell, record, row_index, column_index, store)
  {
    return this.state_tpl.apply({state: data});
  },
  
  renderFilenameCell: function(data, cell, record, row_index, column_index, store)
  {
    return data;
  },
  
  renderNoteCell: function(data, cell, record, row_index, column_index, store)
  {
    var grid = this.grid;
    var f = function() {
      try {
	      Ext.fly(grid.getView().getCell(row_index, column_index)).child('div.x-grid-cell-text').dom['qtip'] = data;
      }
      catch (e) 
      {}
    }
    f.defer(1000);
    return data;
  },
  
  getFileExtension : function(filename)
  {
    var parts = filename.split('.');
    if (parts.length == 1) {
      return null;
    }
    else {
	    return parts.pop();
    }
  },
  
  isPermittedFileType : function(filename)
  {
    if (this.permitted_extensions.length > 0) {
      return this.permitted_extensions.indexOf(this.getFileExtension(filename)) != -1;
    }
    else {
      return true;
    }
  },
  
  updateToolbar : function()
  {
    if (this.is_uploading) {
      //this.toolbar.x_buttons.add.disable();
      this.toolbar.x_buttons.remove.disable();
      this.toolbar.x_buttons.reset.disable();
      if (!this.allow_close_on_upload) {
        this.toolbar.x_buttons.close.disable();
      }
      Ext.fly(this.toolbar.x_buttons.indicator.getEl()).replaceClass(
        'x-ux-uploaddialog-indicator-stoped',
        'x-ux-uploaddialog-indicator-processing'
      );
      this.toolbar.x_buttons.upload.getEl().child('button.x-btn-text').replaceClass(
        'x-ux-uploaddialog-uploadstartbtn',
        'x-ux-uploaddialog-uploadstopbtn'
      );
      this.toolbar.x_buttons.upload.setText(this.i18n.upload_btn_stop_text);
      this.toolbar.x_buttons.upload.enable();
    }
    else {
      //this.toolbar.x_buttons.add.enable();
      this.toolbar.x_buttons.close.enable();  
      Ext.fly(this.toolbar.x_buttons.indicator.getEl()).replaceClass(
        'x-ux-uploaddialog-indicator-processing',      
        'x-ux-uploaddialog-indicator-stoped'
      );
      this.toolbar.x_buttons.upload.getEl().child('button.x-btn-text').replaceClass(
        'x-ux-uploaddialog-uploadstopbtn',  
        'x-ux-uploaddialog-uploadstartbtn'
      );
      this.toolbar.x_buttons.upload.setText(this.i18n.upload_btn_start_text);
       
      var queued_count = this.getQueuedCount();
      
      if (queued_count > 0) {
        this.toolbar.x_buttons.upload.enable();
      }
      else {
        this.toolbar.x_buttons.upload.disable();      
      }
      
      if (this.grid.getSelectionModel().hasSelection()) {
        this.toolbar.x_buttons.remove.enable();
      }
      else {
        this.toolbar.x_buttons.remove.disable();
      }
      
      if (this.store.getCount() > 0) {
        this.toolbar.x_buttons.reset.enable();
      }
      else {
        this.toolbar.x_buttons.reset.disable();
      }
    }
  },
  
  updateProgressbar : function()
  {
    this.progress.setMax(this.initial_queued_count);
    
    if (this.is_uploading) {
      var queued = this.getQueuedCount(true);
      this.progress.setValue(
        this.initial_queued_count - queued,
        String.format(
          this.i18n.progress_uploading_text, 
          this.initial_queued_count - queued,
          this.initial_queued_count
        )
      );
    }
    else {
      this.progress.setValue(0, this.i18n.progress_waiting_text);
    }
  },
  
  startUpload : function()
  {
    if (this.is_uploading) {
      return;
    }
    this.is_uploading = true;
    this.initial_queued_count = this.getQueuedCount();
    this.updateToolbar();    
    this.fireEvent('uploadstart', this);
    this.doUpload();
  },
  
  stopUpload : function()
  {
    if (this.is_uploading) {
      var upload_frame = this.findUploadIframe();
      if (upload_frame) {
      
        this.is_uploading = false; 
        
        Ext.lib.Event.purgeElement(upload_frame, true, 'load');
        upload_frame.src = 'about:blank';
        setTimeout(function(){document.body.removeChild(upload_frame);}, 100);

        var fake_response = {
          responseText: Ext.encode({success: false, message: this.i18n.note_aborted})
        }
        // Faking ajax success call        
        this.onAjaxSuccess(fake_response, {});
        // Faking ajax response call
        this.onAjaxResponse({}, true, fake_response);
        
      }
      this.updateProgressbar();
      this.fireEvent('uploadstop', this);
    }
  },
  
  isUploading : function()
  {
    return this.is_uploading;
  },
  
  getQueuedCount : function(count_processing)
  {
    var count = 0;
    this.store.each(function(r) {
      if (r.get('state') == Ext.ux.UploadDialog.FileRecord.STATE_QUEUE) {
        count++;
      }
      if (count_processing && r.get('state') == Ext.ux.UploadDialog.FileRecord.STATE_PROCESSING) {
        count++;
      }
    });
    return count;
  },
  
  resetQueue : function()
  {
    this.store.each(
      function(r) {
        r.get('input_element').remove();
      },
      this
    );
    this.store.removeAll();
    this.updateToolbar();  
    this.fireEvent('resetqueue', this);
  },
  
  doUpload : function()
  {
    this.updateProgressbar();
    var first_fond = false;
    this.store.each(
      function(r) { 
        if (r.get('state') == Ext.ux.UploadDialog.FileRecord.STATE_QUEUE && !first_fond) {
          r.get('input_element').dom.disabled = false;
          r.set('state', Ext.ux.UploadDialog.FileRecord.STATE_PROCESSING);
          r.set('note', this.i18n.note_processing);
          first_fond = true;
          r.commit();
        }
        else {
          r.get('input_element').dom.disabled = true;      
        }
      },
      this
    );
    if (first_fond) {
      // this is for buttons input file were disabled during form recreation in the hidden iframe
      this.toolbar.x_buttons.add.disableInputFile();
      // requesting
      Ext.Ajax.request({
        url : this.url,
        params : this.base_params || this.baseParams || this.params,
        method : 'POST',
        form : this.form.dom,
        isUpload : true,
        callback : this.onAjaxResponse,
        success : this.onAjaxSuccess,
        failure : this.onAjaxFailure,
        scope : this
      });
      // enabling button back
      this.toolbar.x_buttons.add.enableInputFile();      
    }
  },
  
  getUrl : function()
  {
    return this.url;
  },
  
  setUrl : function(url)
  {
    this.url = url;
  },
  
  getBaseParams : function()
  {
    return this.base_params;
  },
  
  setBaseParams : function(params)
  {
    this.base_params = params;
  },
  
  getUploadAutostart : function()
  {
    return this.upload_autostart;
  },
  
  setUploadAutostart : function(value)
  {
    this.upload_autostart = value;
  },
  
  getAllowCloseOnUpload : function()
  {
    return this.allow_close_on_upload;
  },
  
  setAllowCloseOnUpload : function(value)
  {
    this.allow_close_on_upload;
    this.updateToolbar();
  },
  
  findUploadIframe: function() 
  {
    var ifs = Ext.fly(document.body).query('iframe.x-hidden');
    if (ifs.length > 0) {
      return ifs[0];
    }
    return null;
  },

  onAjaxSuccess : function(response, options)
  {
    var data = {
      'success' : false,
      'error' : this.i18n.note_upload_error
    }
    try {
      var data = Ext.util.JSON.decode(response.responseText);
    }
    catch (e) {
    }
    var record = null;
    this.store.each(function(r) {
      if (r.get('state') == Ext.ux.UploadDialog.FileRecord.STATE_PROCESSING) {
        record = r;
        return false;
      }
    });
    
    if (data.success) {
      record.set('state', Ext.ux.UploadDialog.FileRecord.STATE_FINISHED);
      record.set('note', data.message || data.error || this.i18n.note_upload_success);
      record.commit();
      this.fireEvent('uploadsuccess', this, record.get('filename'), data);
    }
    else {
      record.set('state', Ext.ux.UploadDialog.FileRecord.STATE_FAILED);
      record.set('note', data.message || data.error || this.i18n.note_upload_error);
      record.commit();
      this.fireEvent('uploaderror', this, record.get('filename'), data);      
    }
  },
  
  onAjaxFailure : function(response, options)
  {
    var record = null;
    this.store.each(
      function(r) {
        if (r.get('state') == Ext.ux.UploadDialog.FileRecord.STATE_PROCESSING) {
          r.set('state') = Ext.ux.UploadDialog.FileRecord.STATE_FAILED;
          r.set('note') = this.i18n.note_upload_failed;
          r.commit();
          this.fireEvent('uploadfailed', this, r.get('filename'));
          return false;
        }
      },
      this
    );
  },
  
  onAjaxResponse : function(options, success, response)
  {
    var queued_count = this.getQueuedCount()
    if (queued_count > 0) {
      if (this.is_uploading) {
        this.doUpload.defer(300, this);
      }
    }
    else {
      this.is_uploading = false;
      this.fireEvent('uploadcomplete', this);
    }
    this.updateToolbar(); 
    this.updateProgressbar();
    this.store.applySort();       
    this.grid.getView().refresh();    
  },
  
  onGridRender : function(grid)
  {
    this.createProgressBar();
    this.createGridToolbar();
    this.updateToolbar();
  },
  
  onDialogResize : function(dialog, width, height)
  {
    width -= this.body_delta.width;
    height -= this.body_delta.height;
    this.form.setSize(width, height);
    this.grid.getGridEl().setSize(width, height);
    this.grid.autoSize();
  },
  
  onDialogBeforeHide : function(dialog)
  {
    if (this.is_uploading && !this.allow_close_on_upload) {
      return false;
    }
    return true;
  },
  
  onDialogHide : function(dialog)
  {
    if (this.reset_on_hide) {
      this.resetQueue();
    }
  },
  
  onAddButtonFileSelected : function(button)
  {
    var input_file = button.getInputFile();
    if (this.fireEvent('filetest', this, input_file.dom.value) !== false) {
      if (this.isPermittedFileType(input_file.dom.value)) {
        button.detachInputFile();  
        input_file.hide();        
        input_file.appendTo(this.form);
        this.store.add(
          new Ext.ux.UploadDialog.FileRecord({
            state: Ext.ux.UploadDialog.FileRecord.STATE_QUEUE,
            filename: input_file.dom.value,
            note: this.i18n.note_queued_to_upload,
            input_element: input_file
          })
        );
        this.fireEvent('fileadd', this, input_file.dom.value);
        if (this.upload_autostart && !this.is_uploading) {
          this.startUpload();
        }
        else if (this.is_uploading) {
          this.initial_queued_count++;
        }
      }
      else {
        Ext.Msg.alert(
          this.i18n.error_msgbox_title, 
          String.format(
            this.i18n.err_file_type_not_permitted,
            input_file.dom.value,
            this.permitted_extensions.join(this.i18n.permitted_extensions_join_str)
          )
        );
      }
    }
    this.updateToolbar();
    this.updateProgressbar();
  },
  
  onRemoveButtonClick : function()
  {
    var sel = this.grid.getSelectionModel().getSelections();
    Ext.each(
      sel, 
      function(r) {
        r.get('input_element').remove();
        this.store.remove(r);
        this.fireEvent('fileremove', this, r.get('filename'))
      },
      this
    );
    this.updateToolbar();    
  },
  
  onResetButtonClick : function()
  {
    this.resetQueue();
  },
  
  onUploadButtonClick : function()
  {
    if (this.is_uploading) {
      this.stopUpload();
    }
    else {
      this.startUpload();
    }
  },
  
  onCloseButtonClick : function()
  {
    this.hide();
  },
  
  destroy : function(remove_el)
  {
    this.stopUpload();
    
    this.purgeListeners();
    this.store.purgeListeners();
    this.grid.purgeListeners();
    
    this.resetQueue();
    this.progress.destroy();
    for (var btn in this.toolbar.x_buttons) {
      this.toolbar.x_buttons[btn].destroy();
      this.toolbar.x_buttons[btn] = null;
    }
    
    this.grid.destroy(true, false);
    
    Ext.ux.UploadDialog.Dialog.superclass.destroy.call(this, remove_el);
    
    for (var prop in this) {
      this[prop] = null;
    }
    Ext.Element.garbageCollect();
  }
});

var p = Ext.ux.UploadDialog.Dialog.prototype;
p.i18n = {
  state_col_title: 'State',
  state_col_width: 60,
  filename_col_title: 'Filename',
  note_col_title: 'Note',
  note_col_width: 125,
  add_btn_text: 'Add',
  add_btn_tip: 'Add file into upload queue.',
  remove_btn_text: 'Remove',
  remove_btn_tip: 'Remove file from upload queue.',
  reset_btn_text: 'Reset',
  reset_btn_tip: 'Reset queue.',
  upload_btn_start_text: 'Upload',
  upload_btn_stop_text: 'Abort',
  upload_btn_tip: 'Upload queued files to the server.',
  close_btn_text: 'Close',
  close_btn_tip: 'Close the dialog.',
  progress_waiting_text: 'Waiting...',
  progress_uploading_text: 'Uploading: {0} of {1} files complete.',
  error_msgbox_title: 'Error',
  permitted_extensions_join_str: ',',
  err_file_type_not_permitted: 'Selected file extension isn\'t permitted.<br/>Please select files with following extensions: {1}',
  note_queued_to_upload: 'Queued for upload.',
  note_processing: 'Uploading.',
  note_upload_failed: 'Server is unavailable or internal server error occured.',
  note_upload_success: 'OK.',
  note_upload_error: 'Upload error.',
  note_aborted: 'Aborted by user.',
  state_titles: [
    'Queued for upload.',
    'Successfuly uploaded.',
    'Upload failed.',
    'Processing...'
  ]
}