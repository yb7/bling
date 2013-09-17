Ext.define('Bling.view.sys.ScheduleGrid', {
    extend:'Ext.grid.Panel',
    xtype:'schedule-grid',

    store: 'sys.Schedules',
    initComponent:function() {
        Ext.apply(this, {
            title:'计划列表',
            anchor:'100% 100%',
            columns: [
                {
                    text   : '触发ID',
                    dataIndex: 'triggerKey',
                    width:200
                },
                {
                    text   : '描述',
                    flex    : 1,
                    dataIndex: 'description'
                },
                {
                    text   : '上次执行时间',
                    width    : 150,
                    dataIndex: 'previousFireTime'
                },
                {
                    text   : '下次执行时间',
                    width    : 150,
                    dataIndex: 'nextFireTime'
                },
                {
                    text   : '状态',
                    width    : 80,
                    dataIndex: 'state'
                }  ,
                {
                    text   : '任务ID',
                    width    : 80,
                    dataIndex: 'jobKey'
                }
            ],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                ui: 'footer',
                items:[ '->',
                    {
                        text:'立即生效',
                        itemId:'executeBtn',
                        disabled: true
                    },
                    {
                        text:'删除',
                        itemId:'deleteBtn',
                        disabled: true
                    }
                ]
            }]
        });
        this.callParent(arguments);
    }
})