How to make template for NetsAdmin
==================================

More a discussion document.

1. Portal-transhist

All that is needed is a list of account numbers, but we need texts for the useradmin and for netsadmin.

accounts: list of strings
i18n: account: {no: kontoliste, dk: kontoliste, en: account list}
expansion: named accounts, accounts with attributes, accounts with restriction on search (debit/credit, date restriction)


2. portal-ef-archive

A list of search configurations, or better, a "list" of named search configurations.  E.g.

  { default: {senders: [1,2,3], receivers: [1,2,4], long: true}, 
    extra: {senders: [66], receivers: [], dateFrom: "2005-06-12", dateTo: "2007-12-01"},
    }
    
    FirstKey = configuration name
    i18n: default:{no:standard, en: default, dk:default}
    senders, receivers=list of strings
    long: boolean
    dateFrom, dateTo: date
    
3. TAD

4. UserAdmin

Special challenge: How to set that admin cannot add other admins to the company.

5. 