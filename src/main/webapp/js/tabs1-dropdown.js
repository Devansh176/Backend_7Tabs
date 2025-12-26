Ext.onReady(function () {
    // Define a simple data store
    var staticStore = Ext.create('Ext.data.Store', {
        fields: ['name'],
        data: [
            { "name": "STATIC VALUE 1" },
            { "name": "STATIC VALUE 2" },
            { "name": "STATIC VALUE 3" },
            { "name": "DEPARTMENT A" },
            { "name": "DEPARTMENT B" },
            { "name": "DEPARTMENT C" },
        ]
    });

    // Create the ComboBox
    Ext.create('Ext.form.field.ComboBox', {
        renderTo: 'extDropdown',
        width: 180,
        store: staticStore,
        queryMode: 'local',
        displayField: 'name',
        valueField: 'name',

        //Search feature
        emptyText: 'Type to search...',
        editable: true,
        typeAhead: true,
        typeAheadDelay: 100,
        minChars: 1,
        forceSelection: true,

        // Force the component to be visible immediately
        style: {
            display: 'inline-block',
            marginBottom: '0px' 
        }
    });
});