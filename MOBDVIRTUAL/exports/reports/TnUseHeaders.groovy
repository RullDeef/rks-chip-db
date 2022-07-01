// band 'reportname'
def e = params['element']
return [['reportname': 'Отчет по применяемости элемента ЭКБ: ' + e.name]]

//band 'emptyline1'
return [['el1': ' ']]

//band 'emptyline2'
return [['el2': ' ']]

//band headerup
return [[
                'hsp1'    : ' ',
                'htype'   : 'Тип изделия ЭКБ',
                'htyponom': 'Условное обозначение типономинала',
                'hlevel'  : 'Уровень качества',
                'hmans'   : 'Производители',
                'hcountry': 'Страна'
        ]]

//band componentCommons
def el = params['element']
def rus = e.isRussian()
def companies = e.type.manufacturers

def companiesList = ""
companies.each { c ->
    companiesList += (c.toString() + "; ")
}

return [[
                'sp1'         : ' ',
                'type'        : (e.type) ? e.type.designation : 'Не указано',
                'typonominal' : (e.name) ? e.name : 'Не указано',
                'qualityLevel': e.getQualityCaption(),
                'mans'        : companiesList,
                'country'     : (rus) ? 'Отечественный' : 'Импортный'
        ]]

//band headerdown
return [[
                'henum'    : 'п/п',
                'hdevpt'   : 'Аппаратура (прибор), в котором применяется изделие ЭКБ',
                'hdevptdev': 'Предприятие разработчик аппаратуры (прибора)',
                'hdev'     : 'Изделие РКТ, в котором применяется изделие ЭКБ',
                'hdevprod' : 'Предприятие головной разработчик',
                'hyr'      : 'Год утверждения',
        ]]
