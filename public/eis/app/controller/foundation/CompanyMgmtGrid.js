Ext.define('Bling.controller.foundation.CompanyMgmtGrid', {
    extend: 'Ext.app.Controller',
    requires: ['Bling.view.foundation.EditCompanyWin', 'Bling.model.foundation.Company'],
    views: ['foundation.EditCompanyWin'],
    models: ['foundation.Company'],
    refs: [
        {
            selector: 'company-mgmt-grid',
            ref: 'companyMgmtGrid'
        },
        {
            selector: 'company-mgmt-grid textfield[name=searchField]',
            ref: 'searchField'
        },
        {
            selector: 'company-mgmt-grid statusbar[name=searchStatusBar]',
            ref: 'searchStatusBar'
        }
    ],

//    stores: [
//        'foundation.Companies'
//    ],

    /**
     * @private
     * search value initialization
     */
    searchValue: null,

    /**
     * @private
     * The row indexes where matching strings are found. (used by previous and next buttons)
     */
    indexes: [],

    /**
     * @private
     * The row index of the first search, it could change if next or previous buttons are used.
     */
    currentIndex: null,

    /**
     * @private
     * The generated regular expression used for searching.
     */
    searchRegExp: null,

    /**
     * @private
     * Case sensitive mode.
     */
    caseSensitive: false,

    /**
     * @private
     * Regular expression mode.
     */
    regExpMode: false,

    /**
     * @cfg {String} matchCls
     * The matched string css classe.
     */
    matchCls: 'x-livesearch-match',

    defaultStatusText: 'Nothing Found',

    init: function() {
        var me = this;
        this.control({
            'company-mgmt-grid': {
                afterrender: this.fetchCompanies,
                itemdblclick: this.showEditWin,
                selectionchange: function(selModel, selections){
                    this.getCompanyMgmtGrid().down('#deleteBtn').setDisabled(selections.length === 0);
                }
            },
            'company-mgmt-grid textfield[name=searchField]': {
                change: {
                    fn: me.onTextFieldChange,
                    scope: this,
                    buffer: 100
                }
            },
            'company-mgmt-grid #previousSearch': {
                click: this.onPreviousClick
            },
            'company-mgmt-grid #nextSearch': {
                click: this.onNextClick
            },
            'company-mgmt-grid #regExpToggle': {
                click: this.regExpToggle
            },
            'company-mgmt-grid #caseSensitiveToggle': {
                click: this.caseSensitiveToggle
            },
            'company-mgmt-grid #createBtn': {
                click: function(){this.getView('foundation.EditCompanyWin').create().show();}
            },
            'company-mgmt-grid #deleteBtn': {
                click: this.deleteCompany
            },
            'edit-company-win #saveBtn': {
                click: this.saveCompany
            },
            'edit-company-win #cancelBtn': {
                click: function(cmp, e, opts){ cmp.up('edit-company-win').close(); }
            }


        })
    },
    deleteCompany: function(){
        var selected = this.getCompanyMgmtGrid().getSelectionModel().getSelection()[0];
        if (selected) {
            this.getCompanyMgmtGrid().getStore().remove(selected);
        }
    },

    showEditWin: function(view, record, item, index, e, opts) {
        var win = this.getView('foundation.EditCompanyWin').create();
        win.down('form').getForm().loadRecord(record);
        win.show();
    },

    saveCompany: function(cmp, e, opts) {
        var form  = cmp.up('form');
        if (!form.getForm().isValid()) {
            return;
        }
        var values = form.getForm().getFieldValues();
        if (Ext.isEmpty(values.id)) {
            delete values.id;
            delete values.version;
            var company = Ext.create('Bling.model.foundation.Company', values);
            this.getCompanyMgmtGrid().getStore().insert(0, company);
            cmp.up('edit-company-win').close();
        } else {
            var selected = this.getCompanyMgmtGrid().getSelectionModel().getSelection()[0];
            selected.beginEdit();
            selected.set('name', values.name);
            selected.set('shortCode', values.shortCode);
            selected.endEdit();
            cmp.up('edit-company-win').close();
        }
    },

    fetchCompanies: function(cmp, opts) {
        this.getCompanyMgmtGrid().getStore().load();
    },

    // detects html tag
    tagsRe: /<[^>]*>/gm,

    // DEL ASCII code
    tagsProtect: '\x0f',

    // detects regexp reserved word
    regExpProtect: /\\|\/|\+|\\|\.|\[|\]|\{|\}|\?|\$|\*|\^|\|/gm,

    /**
     * In normal mode it returns the value with protected regexp characters.
     * In regular expression mode it returns the raw value except if the regexp is invalid.
     * @return {String} The value to process or null if the textfield value is blank or invalid.
     * @private
     */
    getSearchValue: function() {
        var me = this,
            value = me.getSearchField().getValue();

        if (value === '') {
            return null;
        }
        if (!me.regExpMode) {
            value = value.replace(me.regExpProtect, function(m) {
                return '\\' + m;
            });
        } else {
            try {
                new RegExp(value);
            } catch (error) {
                me.statusBar.setStatus({
                    text: error.message,
                    iconCls: 'x-status-error'
                });
                return null;
            }
            // this is stupid
            if (value === '^' || value === '$') {
                return null;
            }
        }

        return value;
    },
    /**
     * Finds all strings that matches the searched value in each grid cells.
     * @private
     */
    onTextFieldChange: function() {
        var me = this,
            count = 0;

        this.getCompanyMgmtGrid().view.refresh();
        // reset the statusbar
        this.getSearchStatusBar().setStatus({
            text: me.defaultStatusText,
            iconCls: ''
        });

        me.searchValue = me.getSearchValue();
        me.indexes = [];
        me.currentIndex = null;

        if (me.searchValue !== null) {
            me.searchRegExp = new RegExp(me.searchValue, 'g' + (me.caseSensitive ? '' : 'i'));


            this.getCompanyMgmtGrid().store.each(function(record, idx) {
                var td = Ext.fly(me.getCompanyMgmtGrid().view.getNode(idx)).down('td'),
                    cell, matches, cellHTML;
                while(td) {
                    cell = td.down('.x-grid-cell-inner');
                    matches = cell.dom.innerHTML.match(me.tagsRe);
                    cellHTML = cell.dom.innerHTML.replace(me.tagsRe, me.tagsProtect);

                    // populate indexes array, set currentIndex, and replace wrap matched string in a span
                    cellHTML = cellHTML.replace(me.searchRegExp, function(m) {
                        count += 1;
                        if (Ext.Array.indexOf(me.indexes, idx) === -1) {
                            me.indexes.push(idx);
                        }
                        if (me.currentIndex === null) {
                            me.currentIndex = idx;
                        }
                        return '<span class="' + me.matchCls + '">' + m + '</span>';
                    });
                    // restore protected tags
                    Ext.each(matches, function(match) {
                        cellHTML = cellHTML.replace(me.tagsProtect, match);
                    });
                    // update cell html
                    cell.dom.innerHTML = cellHTML;
                    td = td.next();
                }
            }, me);

            // results found
            if (me.currentIndex !== null) {
                me.getCompanyMgmtGrid().getSelectionModel().select(me.currentIndex);
                me.getSearchStatusBar().setStatus({
                    text: count + ' matche(s) found.',
                    iconCls: 'x-status-valid'
                });
            }
        }

        // no results found
        if (me.currentIndex === null) {
            me.getCompanyMgmtGrid().getSelectionModel().deselectAll();
        }

        // force textfield focus
        me.getSearchField().focus();
    },
    /**
     * Selects the previous row containing a match.
     * @private
     */
    onPreviousClick: function() {
        var me = this,
            idx;

        if ((idx = Ext.Array.indexOf(me.indexes, me.currentIndex)) !== -1) {
            me.currentIndex = me.indexes[idx - 1] || me.indexes[me.indexes.length - 1];
            me.getCompanyMgmtGrid().getSelectionModel().select(me.currentIndex);
        }
    },

    /**
     * Selects the next row containing a match.
     * @private
     */
    onNextClick: function() {
        var me = this,
            idx;

        if ((idx = Ext.Array.indexOf(me.indexes, me.currentIndex)) !== -1) {
            me.currentIndex = me.indexes[idx + 1] || me.indexes[0];
            me.getCompanyMgmtGrid().getSelectionModel().select(me.currentIndex);
        }
    },

    /**
     * Switch to case sensitive mode.
     * @private
     */
    caseSensitiveToggle: function(checkbox, checked) {
        this.caseSensitive = checked;
        this.onTextFieldChange();
    },

    /**
     * Switch to regular expression mode
     * @private
     */
    regExpToggle: function(checkbox, checked) {
        this.regExpMode = checked;
        this.onTextFieldChange();
    }
});
