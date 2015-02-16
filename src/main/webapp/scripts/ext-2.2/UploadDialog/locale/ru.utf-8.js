Ext.apply(
  Ext.ux.UploadDialog.Dialog.prototype.i18n,
  {
    state_col_title: 'Статус',
    state_col_width: 70,
    filename_col_title: 'Имя файла',
    note_col_title: 'Пояснение',
    note_col_width: 150,
    add_btn_text: 'Добавить',
    add_btn_tip: 'Добавить файл в очередь загрузки.',
    remove_btn_text: 'Удалить',
    remove_btn_tip: 'Удалить файл из очереди загрузки.',
    reset_btn_text: 'Очистить',
    reset_btn_tip: 'Очистить очередь загрузки.',
    upload_btn_start_text: 'Загрузить',
    upload_btn_stop_text: 'Отменить',
    upload_btn_tip: 'Загрузить файлы на сервер.',
    close_btn_text: 'Закрыть',
    close_btn_tip: 'Закрыть диалог загрузки.',
    progress_waiting_text: 'Жду...',
    progress_uploading_text: 'Загружаю: {0} из {1} файлов загружено.',
    error_msgbox_title: 'Ошибка',
    permitted_extensions_join_str: ',',
    err_file_type_not_permitted: 'Файлы с таким расширением запрещено загружать.<br/>Пожалуйста выбирайте файлы с одним из следующий расширений: {1}',
    note_queued_to_upload: 'Поставлен в очередь.',
    note_processing: 'Загружается...',
    note_upload_failed: 'Сервер недоступен, либо на сервере произошла ошибка.',
    note_upload_success: 'Загружен.',
    note_upload_error: 'Ошибка загрузки.',
    note_aborted: 'Загрузка прервана пользователем.',
    state_titles: [
      'Поставлен в очередь.',
      'Успешно загружен.',
      'Не удалось загрузить файл.',
      'Загружается...'
    ]
  }
);