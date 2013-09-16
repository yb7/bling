Ext.define('Bling.view.shared.RedOrBlueCombo', {
    extend:'Ext.form.field.ComboBox',
    xtype: 'red-or-blue-combo',
    requires: ['Ext.data.Store'],

    store: Ext.create('Ext.data.Store', {
        fields: ['name'],
        data : [
            {"name":"红字"},
            {"name":"蓝字"}
        ]
    }),
    initComponent:function() {
        var me = this;
        var config = {
            queryMode: 'local',
            displayField: 'name',
            valueField: 'name',

            selectOnFocus:true,
            forceSelection:true
        };
        Ext.apply(this, config);

        this.on('change', function(){
            if (me.getValue() === null) {
                me.reset();
            }
        });


        this.callParent(arguments);
    }
});