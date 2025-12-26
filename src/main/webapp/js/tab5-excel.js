var isTab5Loaded = false;

window.initTab5 = function() {
    if (isTab5Loaded) return;
    isTab5Loaded = true;

    Ext.onReady(function() {

        var uploadForm = Ext.create('Ext.form.Panel', {
            title: 'Excel Upload',
            bodyPadding: 10,
            width: 500,
            height: 150,
            renderTo: 'tab5-container',
            defaults: { anchor: '100%', labelWidth: 100 },
            items: [
                {
                    xtype: 'filefield',
                    name: 'excelFile',
                    fieldLabel: 'Choose Excel',
                    buttonText: 'Browse...',
                    allowBlank: false
                }
            ],
            buttons: [
                {
                    text: 'Upload',
                    icon: 'https://cdn-icons-png.flaticon.com/16/724/724933.png',
                    handler: function() {
                        var form = this.up('form').getForm();
                        if (form.isValid()) {
                            form.submit({
                                url: 'uploadPrefixExcel',
                                waitMsg: 'Uploading your excel...',
                                success: function(fp, o) {
                                    Ext.Msg.alert('Success', 'File Uploaded and Data Saved!');
                                },
                                failure: function(fp, o) {
                                    Ext.Msg.alert('Error', 'File upload failed.');
                                }
                            });
                        }
                    }
                }
            ]
        });

        Ext.create('Ext.panel.Panel', {
            title: 'Excel Download',
            bodyPadding: 10,
            width: 500,
            margin: '20 0 0 0',
            renderTo: 'tab5-container',
            items: [
                {
                    xtype: 'button',
                    text: 'Download Data from DB',
                    scale: 'medium',
                    icon: 'https://cdn-icons-png.flaticon.com/16/724/724933.png',
                    handler: function() {
                        window.open('downloadPrefixExcel', '_blank');
                    }
                }
            ]
        });
    });
};