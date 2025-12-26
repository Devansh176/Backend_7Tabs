window.openEditorPopup = function() {
    console.log("Attempting to open popup...");

    // 1. Safety Check
    if (typeof Ext === 'undefined') {
        alert("ExtJS still not loaded! Check your <head> links.");
        return;
    }

    // 2. Create Window
    Ext.create('Ext.window.Window', {
        title: 'Patient Details',
        modal: true,
        width: 800,
        height: 550,
        layout: {
            type: 'vbox',
            align: 'stretch'
        },
        bodyPadding: 10,
        closeAction: 'destroy',

        items: [
            // --- TOP SECTION: Patient Info ---
            {
                xtype: 'container',
                height: 200,
                layout: 'hbox',
                style: {
                    borderBottom: '1px solid #ccc',
                    marginBottom: '10px',
                    paddingBottom: '10px'
                },
                items: [
                    // Column 1
                    {
                        xtype: 'component',
                        flex: 1,
                        html: '<div style="line-height:1.8; font-family:Arial; font-size:12px;">' +
                              '<b>Name</b> : Devansh<br>' +
                              '<b>MRN</b> : 93874<br>' +
                              '<b>Date Of Birth</b> : 17-06-2004<br>' +
                              '<b>Age</b> : 21<br>' +
                              '<b>Gender</b> : M<br>' +
                              '<b>Address</b> : NGP<br>' +
                              '<b>Reg Date</b> : 23-12-2025<br>' +
                              '<b>Status</b> : Nice' +
                              '</div>'
                    },
                    // Column 2
                    {
                        xtype: 'component',
                        flex: 1,
                        html: '<div style="line-height:1.8; font-family:Arial; font-size:12px;">' +
                            '<div style="font-size:15p; font-weight:bold;">Test done:</div>'+
                            'CT SCAN<br>' +
                            'CYTOLOGY<br>' +
                            'DIGITAL X-RAY<br>' +
                            'FLUID EXAMINATION<br>' +
                            'GASTROENTEROLOGY INVESTIGATION<br>' +
                            'HAEMATOLOGY<br>' +
                            'HARMONES<br>' +
                            'HISTOPATHOLOGY' +
                            '</div>'
                    }
                ]
            },
            
            // --- MIDDLE SECTION: Label ---
            {
                xtype: 'component',
                html: '<b>Text Editor</b>',
                margin: '0 0 5 0'
            },

            // --- BOTTOM SECTION: HTML Editor ---
            {
                xtype: 'htmleditor',
                flex: 1, 
                enableColors: true,
                enableAlignments: true,
                value: 'CT SCAN, CYTOLOGY, DIGITAL X-RAY, FLUID EXAMINATION, GASTROENTEROLOGY INVESTIGATION'
            }
        ],

        buttons: [{
            text: 'Close',
            handler: function(btn) {
                btn.up('window').close();
            }
        }]
    }).show();
};