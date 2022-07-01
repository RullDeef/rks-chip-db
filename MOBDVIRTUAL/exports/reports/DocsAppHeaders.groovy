// band reportname
def name = 'Статистика по согласованию документов за период с '
def from = params['startDate']
def to = params['endDate']
name += String.format('%02d.%02d.%04d', from[Calendar.DAY_OF_MONTH], from[Calendar.MONTH] + 1, from[Calendar.YEAR])
name += ' по '
name += String.format('%02d.%02d.%04d', to[Calendar.DAY_OF_MONTH], to[Calendar.MONTH] + 1, to[Calendar.YEAR])
return [['name': name]]

// band headerone
return [[
                'docname'   : 'Наименование документа',
                'ready'     : 'Согласовано',
                'inprogress': 'В процессе согласования',
                'gnio'      : 'На согласовании у ГНИО РКП по ЭКБ',
                'mniirip'   : 'На согласовании у ФГУП "МНИИРИП"',
                'niikp'     : 'На согласовании у Филиала АО «ОРКК»  – «НИИ КП»',
                'declined'  : 'Отклонено',
                'total'     : 'Всего',
                'support'   : 'Обращения в службу технической поддежки на тему "Согласование документов"'
        ]]

//band headerone
return [[
                'henum'             : '№ п/п',
                'hdevname'          : "Обозначение изделия РКТ",
                'himportincluded'   : 'Иностранных радиоэлектронных изделий, примененных в данном изделии РКТ',
                'hnativeincluded'   : 'Отечественные радиоэлектронных изделий, примененных в данном изделии РКТ',
                'htotalincluded'    : 'Общее количество радиоэлектронных изделий, применённых в данном изделии РКТ, шт.',
                'hsubstitutionlevel': 'Уровень импортазамещения радиоэлектронных изделий в изделии РКТ, %',
                'hdependencylevel'  : 'Степерь зависимости разработки изделия РКТ от импортных изделий',
        ]]

//band headertwo
return [[
                'pcs': 'шт.',
                'prc': '%'
        ]]

//band headertwo
return [[
                'hpcs': 'шт.',
                'hprc': '%'
        ]]
