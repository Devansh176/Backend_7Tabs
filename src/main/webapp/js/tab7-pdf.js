var isTab7Loaded = false;

window.initTab7 = function() {
    if (isTab7Loaded) return;
    isTab7Loaded = true;

    Ext.onReady(function() {
        Ext.create('Ext.panel.Panel', {
            title: 'Tab 7: Puppeteer PDF Generation',
            renderTo: 'tab7-container',
            bodyPadding: 20,
            width: '100%',
            height: 300,
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'center'
            },
            items: [
                {
                    xtype: 'button',
                    text: 'Generate PDF',
                    scale: 'large',
                    width: 250,
                    handler: function() {
                        // Trigger the download
                        window.open('downloadPuppeteerPdf', '_blank');
                    }
                }
            ]
        });
    });
};