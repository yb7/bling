Ext.define('Bling.view.shared.CompanyCombo', {
    extend:'Ext.form.field.ComboBox',
    xtype: 'company-combo',

    store: 'foundation.Companies',
    initComponent:function() {
        var me = this;
        var config = {
            valueField: 'id',
            displayField: 'name',
            typeAhead: true,
            selectOnFocus:true,
            queryParam:'shortCode',
            forceSelection:true,
            minChars:1,
            listConfig: {
                loadingText: '查找中...',
                emptyText: '未找到匹配值',
                width:275,
                height:240,
                itemSelector : 'div.search-item',
                getInnerTpl: function() {
                    return '<div class="search-item">' + '<span>{shortCode}-{name}</span>' + '{excerpt}' + '</div>';
                }
            }
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