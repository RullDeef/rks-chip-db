// band 'emptyLine1'
return [['el11':' ', 'el12':' ', 'el13':' ', 'el14':' ']]

// band 'emptyLine2'
return [['el21':' ', 'el22':' ', 'el23':' ', 'el24':' ']]

// band 'emptyLine3'
return [['el31':' ', 'el32':' ', 'el33':' ', 'el34':' ']]

// band 'emptyLine4'
return [['el41':' ', 'el42':' ', 'el43':' ', 'el44':' ']]

// band 'reportname'
//one
return [['reportname':'Потребность предприятий в изделии ЭКБ']]
//all
return [['reportname':'Потребность предприятий в изделиях ЭКБ']]
//company
def m = params['manufacturer']
return [['reportname': 'Потребность предприятий ракетно-космической отрасли в изделиях ЭКБ, производимых ' + m.smartName]]

//band chdes
return [['hdesignation': 'Условное обозначение']]
//band cdes
def e = params['element']
return [['designation': (e.name) ? e.name : 'Не указано' ]]

//band chtype
return [['htype': 'Тип']]
//band ctype
def el = params['element']
return [['type': (e.type) ? el.type.designation : 'Не указано']]

//band chqlevel
return [['hqlevel': 'Уровень качества']]
//band cqlevel
def elm = params['element']
def rus = e.isRussian()
return [['qlevel': e.getQualityCaption()]]

//band chyr
return [['hyr': 'Год']]
//band cyr
def cyr = params['yr']
return [['yr': yr.toString()]]

//band yr
def yr = params['yr']
return [[
		'hyr': 'Год',
		'hyrspace': ' ',
		'yr': yr.toString()
	]]

//band hmain (all)
return [[
		'henum': 'п/п',
		'hdesignation': 'Изделие ЭКБ',
		'hqlevel': 'Уровень качества',
		'hamount': 'Количество',
		'hcompanies': 'Предприятия, испытывающие потребность',
		'hdevelopers': 'Производители'
	]]
//band hmain (company)
return [[
				'henum': 'п/п',
				'hdesignation': 'Изделие ЭКБ',
				'hqlevel': 'Уровень качества',
				'hcompany': 'Предприятия, испытывающие потребность',
				'hamount': 'Количество'
		]]

//band hmain (one)
return [[
    'henum': 'п/п',
    'hcompany': 'Предприятие',
    'hamount': 'Количество'
    ]]
