// band main
def result = []
def currentElement = params['element']
def currentYear = params['yr']
int enumerator = 0
def total = 0
def QUERY_STRING = 'select cn from mobdekbkp$CompanyNeed cn' + 
    ' where cn.typonominal.id = ?1'
def QUERY_YEAR = ' and (extract(year from cn.wantedDeliverDate) = ?2)'

transactional { em->
    def listEntries;
	if (currentYear == null) {
		listEntries = em.createQuery(QUERY_STRING)
		listEntries.setParameter(1, currentElement.id)
	} else {
	    currentYear = Integer.parseInt(params['yr'])
		listEntries = em.createQuery(QUERY_STRING + QUERY_YEAR)
		listEntries.setParameter(1, currentElement.id)
		listEntries.setParameter(2, currentYear)
	}
    listEntries.resultList.each { e->
        enumerator++
        result.add([
            'enum': enumerator,
            'company': e.company.smartName,
            'amount': e.amount
        ])
        total += e.amount
    }
    result.add([
        'enum': 'Итого',
        'company': ' ',
        'amount': total
	])
}
return result
