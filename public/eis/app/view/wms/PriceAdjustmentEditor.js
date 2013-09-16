Ext.define('Bling.view.wms.PriceAdjustmentEditor', {
    extend: 'Ext.panel.Panel',
    xtype: 'price-adjustment-editor',
    requires: ['Bling.view.wms.TallyArticleGrid'],

    bodyPadding: 10,
    title: '分销调拨维护',
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
                { fieldLabel: '个位不能为', boxLabel: '0', name: 'excludeUnits0' },
                { checked: true, boxLabel: '1', name: 'excludeUnits1', labelWidth: 20 },
                { checked: true, boxLabel: '2', name: 'excludeUnits2', labelWidth: 20 },
                { checked: true, boxLabel: '3', name: 'excludeUnits3', labelWidth: 20 },
                { checked: true, boxLabel: '4', name: 'excludeUnits4', labelWidth: 20 },
                { boxLabel: '5', name: 'excludeUnits5', labelWidth: 20 },
                { checked: true, boxLabel: '6', name: 'excludeUnits6', labelWidth: 20 },
                { checked: true, boxLabel: '7', name: 'excludeUnits7', labelWidth: 20 },
                { boxLabel: '8', name: 'excludeUnits8', labelWidth: 20 },
                { boxLabel: '9', name: 'excludeUnits9', labelWidth: 20 }
            ]
        };

        var form = {
            xtype: 'form',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [ {
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
                    name:'distributionDate'
                }]
            }, excludeUnits, {
                xtype: 'fieldset',
                title: '指定售价',
                defaults:{
                    margin:'5 5 5 5'
                },
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '零售价',
                    labelWidth: 110
                }]
            }, {
                xtype: 'fieldset',
                layout: 'hbox',
                title: '按成本调价=(总成本+增量)×批调指数',
                fieldDefaults: {
                    labelWidth: 110
                },
                defaults:{
                    margin:'5 5 5 5'
                },
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '增量'
                }, {
                    xtype: 'textfield',
                    fieldLabel: '批调整指数'
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
                    xtype: 'textfield',
                    fieldLabel: '调前零售价+增量'
                }, {
                    xtype: 'textfield',
                    fieldLabel: '调前零售价×系数'
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
                            text: '执行调拨'
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