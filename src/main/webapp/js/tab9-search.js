var isTab9Loaded = false;

window.initTab9 = function() {
    if(isTab9Loaded) return;
    isTab9Loaded = true;

    Ext.onReady(function() {
        var searchStore = Ext.create('Ext.data.Store', {
            fields: ['id', 'title', 'gender', 'displayPrefix'],
            proxy: {
                type: 'ajax',
                url: 'api/tab9/search',
                reader: {
                    type: 'json'
                }
            },
            autoLoad: false
        });

        Ext.create('Ext.panel.Panel', {
            title: 'Tab9: Debounced Search',
            renderTo: 'tab9-container',
            width: '100%',
            height: 500,
            bodyPadding: 20,
            layout: 'vbox',
            items: [
                {
                    xtype: 'component',
                    html: '<p>Type below. Waits 2 seconds after you stop typing before loading.</p>',
                    margin: '0 0 10 0',
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Search Database',
                    emptyText: 'Type Title...',
                    width: 400,
                    enableKeyEvents: true, // Required to listen to typing
                    listeners: {
                        change: {
                            fn: function(field, newValue) {
                                if (!newValue || newValue.length < 1) {
                                    searchStore.removeAll();
                                    return;
                                }
                                console.log("User stopped typing. Hitting Backend now...");

                                // Trigger the Backend Call
                                searchStore.load({
                                    params: { q: newValue }
                                });
                            },
                            buffer: 2000 // 2 Seconds Delay
                        }
                    }
                },
                {
                    xtype: 'grid',
                    title: 'Search Results',
                    store: searchStore,
                    width: '100%',
                    flex: 1,
                    margin: '20 0 0 0',
                    emptyText: 'No records found',
                    columns: [
                        { text: 'ID', dataIndex: 'id', width: 50 },
                        { text: 'Title', dataIndex: 'title', flex: 1 },
                        { text: 'Gender', dataIndex: 'gender', flex: 1 },
                        { text: 'Prefix', dataIndex: 'displayPrefix', flex: 1 }
                    ]
                },
            ]
        });
    });
}