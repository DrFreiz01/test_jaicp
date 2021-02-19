#подключаем модуль слот-филлинга
require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
#подключаем just-tour.sc
require: themes/weather.sc
require: themes/tour.sc
# библиотека с вспомогвтельными функциями
require: functions.js

# транслитерация
require: jarolit.js


require:  translation.yaml
    name = trans
    var = trans
        