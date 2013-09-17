Ext.define('Bling.view.wms.PriceAdjustmentEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'price-adjustment-editor',
    requires: ['Bling.view.wms.TallyArticleGrid',
        'Ext.form.FieldSet'],

    bodyPadding: 10,
    title: '调价',
    closable:true,

    initComponent: function() {
        var me = this;

        var excludeUnits = {
            xtype: 'container',
//            flex: 1,
//            title: 'Individual Checkboxes',
            defaultType: 'checkbox', // each item will be a checkbox
            layout: 'hbox',
            defaults: {
                margin:'5 5 5 5',
                hideEmptyLabel: false
            },
            items: [
                { fieldLabel: '个位不能为', boxLabel: '0', name: 'excludeUnits0', inputValue: true },
                { checked: true, boxLabel: '1', name: 'excludeUnits1', labelWidth: 20, inputValue: true },
                { checked: true, boxLabel: '2', name: 'excludeUnits2', labelWidth: 20, inputValue: true },
                { checked: true, boxLabel: '3', name: 'excludeUnits3', labelWidth: 20, inputValue: true },
                { checked: true, boxLabel: '4', name: 'excludeUnits4', labelWidth: 20, inputValue: true },
                { boxLabel: '5', name: 'excludeUnits5', labelWidth: 20, inputValue: true },
                { checked: true, boxLabel: '6', name: 'excludeUnits6', labelWidth: 20, inputValue: true },
                { checked: true, boxLabel: '7', name: 'excludeUnits7', labelWidth: 20, inputValue: true },
                { boxLabel: '8', name: 'excludeUnits8', labelWidth: 20, inputValue: true },
                { boxLabel: '9', name: 'excludeUnits9', labelWidth: 20, inputValue: true }
            ]
        };

        var form = {
            xtype: 'form',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [ {
                xtype:'hidden',
                name:'id'
            }, {
                xtype:'hidden',
                name:'version'
            }, {
                xtype: 'container',
                layout: 'hbox',
                defaults:{
                    margin:'5 5 5 5'
                },
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '调价单号',
                    name: 'bizCode',
                    disabled: true
                }, {
                    xtype:'datefield',
                    fieldLabel:'生效日期',
                    format:'Y-m-d',
                    name:'effectiveDate'
                }]
            }, excludeUnits, {
                xtype: 'textarea',
                name: 'remark',
                fieldLabel: '备注'
            }, {
                xtype: 'container',
                layout: 'hbox',
                items: [{
                    xtype: 'fieldset',
                    title: '指定售价',
                    defaults:{
                        margin:'5 5 5 5'
                    },
                    items: [{
                        xtype: 'numberfield',
                        value: 0,
                        minValue: 0,
                        fieldLabel: '零售价',
                        labelWidth: 50,
                        name:'retailPriceDirectly'
                    }],
                    margins: {right:20}
                }, {
                    xtype: 'fieldset',
                    layout: 'hbox',
                    title: '按成本调价=(总成本+增量)×批调指数',
                    defaults:{
                        margin:'5 5 5 5'
                    },
                    items: [{
                            xtype: 'numberfield',
                            value: 0,
                            minValue: 0,
                            fieldLabel: '增量',
                            labelWidth: 50,
                            name:'incrementBaseOnCost'
                        }, {
                            xtype: 'numberfield',
                            value: 0,
                            fieldLabel: '批调整指数',
                            labelWidth: 80,
                            name:'coefficientBaseOnCost'
                    }]
                }]
            }, {
                xtype: 'fieldset',
                layout: 'hbox',
                title: '按调前零售价调价',
                fieldDefaults: {
                    labelWidth: 110
                },
                defaults:{
                    margin:'5 5 5 5'
                },
                items: [{
                    xtype: 'numberfield',
                    value: 0,
                    minValue: 0,
                    fieldLabel: '调前零售价+增量',
                    name:'incrementBaseOnRetailPrice'
                }, {
                    xtype: 'numberfield',
                    value: 0,
                    fieldLabel: '调前零售价×系数',
                    name:'coefficientBaseOnRetailPrice'
                }]
            }]
        };
        var grid = {
            xtype: 'tally-article-grid',
            flex:1,
            store: me.articleStore,
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    ui: 'footer',
                    items: [ '->',
                        {
                            xtype: 'button',
                            itemId: 'addArticlesBtn',
                            text: '添加货品'
                        }, {
                            xtype: 'button',
                            itemId: 'deleteBtn',
                            disabled: true,
                            text: '删除'
                        }, '-', {
                            xtype: 'button',
                            itemId: 'executeBtn',
                            text: '执行调价'
                        }
                    ]
                }
            ]
        }

        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [form, grid]
        });

        me.callParent(arguments);
    }

});