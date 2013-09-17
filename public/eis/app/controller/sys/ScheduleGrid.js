Ext.define('Bling.controller.sys.ScheduleGrid', {
    extend: 'Ext.app.Controller',
//    views: ['foundation.EditCompanyWin'],
//    models: ['foundation.Company'],
    refs: [
        {
            selector: 'schedule-grid',
            ref: 'scheduleGrid'
        }
    ],

//    stores: [
//        'foundation.Companies'
//    ],


    init: function() {
        var me = this;
        this.control({
            'schedule-grid': {
                afterrender: function(grid) {
                    grid.getStore().load();
                },
                selectionchange: function(selModel, selections){
                    this.getScheduleGrid().down('#executeBtn').setDisabled(selections.length === 0);
                    this.getScheduleGrid().down('#deleteBtn').setDisabled(selections.length === 0);
                }
            },
            'schedule-grid #executeBtn': {
                click: this.executeTrigger
            },
            'schedule-grid #deleteBtn': {
                click: this.deleteTrigger
            }
        })
    },

    executeTrigger: function(cmp) {
        var me = this;
        var selected = this.getScheduleGrid().getSelectionModel().getSelection()[0];
        Ext.Ajax.request({
            url: '/eis/sys/job/triggers/' + selected.data.triggerKey + '/execute',
            method:'PUT',
            jsonData:{},
            success: function() {
                me.getScheduleGrid().getStore().load();
            },
            failure:function(response){
                var e = Ext.decode(response.responseText);
                Ext.Msg.alert('警告', e.message);
            }
        });
    },

    deleteTrigger: function(cmp) {
        var selected = this.getScheduleGrid().getSelectionModel().getSelection()[0];
        if (selected) {
            this.getScheduleGrid().getStore().remove(selected);
        }
    }

});
