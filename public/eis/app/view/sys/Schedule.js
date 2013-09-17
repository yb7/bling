Ext.define('JT.view.auth.task.Schedule', {
    extend:'Ext.grid.Panel',
    alias:'widget.schedule',
    requires:['JT.view.auth.task.QuickNewTaskMenu'],
    initComponent:function() {
        var store = Ext.create('JT.store.auth.task.Schedule');
        Ext.apply(this, {
            frame: true,
            store: store,
            title:'计划列表',
            anchor:'100% 100%',
            maxHeight:400,
            columns: [
                {
                    text   : '触发ID',
                    dataIndex: 'triggerKey',
                    width:200
                },
                {
                    text   : '描述',
                    width    : 200,
                    dataIndex: 'description'
                },
                {
                    text   : '上次执行时间',
                    width    : 150,
                    dataIndex: 'previousFireTime',
                    flex: 1
                },
                {
                    text   : '下次执行时间',
                    width    : 150,
                    dataIndex: 'nextFireTime',
                    flex: 1
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
            bbar:Ext.create('Ext.PagingToolbar', {
                store:store
            }),
            tbar:[
                {
                    text:'立即生效',
                    itemId:'executeBtn',
                    hidden:true,
                    iconCls:'silk-time-go'
                },
                Ext.create('JT.view.auth.task.QuickNewTaskMenu'),
                {
                    text:'新增',
                    itemId:'addBtn',
                    iconCls:'silk-add'
                },
                {
                    text:'删除',
                    itemId:'delBtn',
                    iconCls:'silk-cancel'
                }
            ]
        });
        this.callParent(arguments);
    }
})