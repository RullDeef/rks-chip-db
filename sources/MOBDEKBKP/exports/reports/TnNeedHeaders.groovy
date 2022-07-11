// band 'reportname'
return [['reportname':'Отчет по потребности в элементе ЭКБ']]

//band 'emptyline'
return [['el2':' ']]

//band headerup
return [[
    'hnum':'п/п',
    'hgroup':'Группа',
    'htyponom':'Условное обозначение ЭРИ',
    'hlevel':'Уровень качества',
    'hprod':'Фирма-производитель ЭРИ',
    'hcountry':'Страна'
    ]]

//band componentCommons
def e = params['element']
def rus = e.isRussian
def companies = e.type.manufacturers

def companiesList = ""
companies.each { c ->
    companiesList += c.name.smartName
    if (!companies.last()) companiesList += "; "
}

def classname
transactional { em->
    def q = em.createQuery('SELECT tc ' +
            'FROM mobdekbkp$Typonominal tn ' +
            'JOIN mobdekbkp$Type tp on tp.id = tn.type.id ' +
            'JOIN mobdekbkp$TypeClass tc on tc.id = tp.typeClass.id ' +
            'WHERE tn.id = ?1')
    q.setParameter(1, e.id)

    q.resultList.each{ el -> classname = el.getName()}
}

return [[
                'sp1': ' ',
                'rowcl': classname,
                'type': (e.type) ? e.type.designation : 'Не указано',
                'qualityLevel': e.getQualityCaption(),
                'mans': companiesList,
                'country': (rus) ? 'Отечественный' : 'Импортный'
        ]]

//band headerdown
return [[
    'hyr':'Год *',
    'hdev':'Изделия в которых применяется элемент ЭРИ',
    'qty':'шт.',
    'hcommneed':'Общая потребность в элементе ЭКБ, шт.',
    'hdone':'Укомплектовано шт.',
    'hleft':'Необходимо укомплектовать шт.'
    ]]
