/* Преобразование строки в латиницу и обратно согласно стандарту Яролит:

Русский алфавит:

a b v g d e jo zh z i jj k l m n o p r s t u f kh c ch sh shh jq y q eh ju ja

Яролит соответствует ГОСТ 16876-71, таблица 2, но в яролите Ь = Q и Ъ = JQ

Английские буквы вводятся с помощью переключателя xe.

xeSome English Text

При этом в английском режиме x кодируется как xx, w - как ww. 

xr - переключение обратно на русский

Не ASCII, не кириллица = xuNNNNNNx

*/

function ПреобразуйСтрокуВЛатиницу(Рус) {

  function ПреобразуйБуквуВЛатиницу(Буква, з) {

    /// пя = ПоставитьЯзык 
    function пя(НужныйЯзык, текст) {
      if (з.знач == "r" && НужныйЯзык == "e") {
        з.знач = "e";
        return "xe" + текст;
      } else if (з.знач == "e" && НужныйЯзык == "r") {
        з.знач = "r";
        return "xr" + текст;
      } else {
        return текст;
      }
    };

    switch (Буква) {
      case "а": return пя("r", "a"); break;
      case "б": return пя("r", "b"); break;
      case "в": return пя("r", "v"); break;
      case "г": return пя("r", "g"); break;
      case "д": return пя("r", "d"); break;
      case "е": return пя("r", "e"); break;
      case "ё": return пя("r", "jo"); break;
      case "ж": return пя("r", "zh"); break;
      case "з": return пя("r", "z"); break;
      case "и": return пя("r", "i"); break;
      case "й": return пя("r", "jj"); break;
      case "к": return пя("r", "k"); break;
      case "л": return пя("r", "l"); break;
      case "м": return пя("r", "m"); break;
      case "н": return пя("r", "n"); break;
      case "о": return пя("r", "o"); break;
      case "п": return пя("r", "p"); break;
      case "р": return пя("r", "r"); break;
      case "с": return пя("r", "s"); break;
      case "т": return пя("r", "t"); break;
      case "у": return пя("r", "u"); break;
      case "ф": return пя("r", "f"); break;
      case "х": return пя("r", "kh"); break;
      case "ц": return пя("r", "c"); break;
      case "ч": return пя("r", "ch"); break;
      case "ш": return пя("r", "sh"); break;
      case "щ": return пя("r", "shh"); break;
      case "ъ": return пя("r", "jq"); break;
      case "ы": return пя("r", "y"); break;
      case "ь": return пя("r", "q"); break;
      case "э": return пя("r", "eh"); break;
      case "ю": return пя("r", "ju"); break;
      case "я": return пя("r", "ja"); break;

      case "А": return пя("r", "A"); break;
      case "Б": return пя("r", "B"); break;
      case "В": return пя("r", "V"); break;
      case "Г": return пя("r", "G"); break;
      case "Д": return пя("r", "D"); break;
      case "Е": return пя("r", "E"); break;
      case "Ё": return пя("r", "JO"); break;
      case "Ж": return пя("r", "ZH"); break;
      case "З": return пя("r", "Z"); break;
      case "И": return пя("r", "I"); break;
      case "Й": return пя("r", "JJ"); break;
      case "К": return пя("r", "K"); break;
      case "Л": return пя("r", "L"); break;
      case "М": return пя("r", "M"); break;
      case "Н": return пя("r", "N"); break;
      case "О": return пя("r", "O"); break;
      case "П": return пя("r", "P"); break;
      case "Р": return пя("r", "R"); break;
      case "С": return пя("r", "S"); break;
      case "Т": return пя("r", "T"); break;
      case "У": return пя("r", "U"); break;
      case "Ф": return пя("r", "F"); break;
      case "Х": return пя("r", "KH"); break;
      case "Ц": return пя("r", "C"); break;
      case "Ч": return пя("r", "CH"); break;
      case "Ш": return пя("r", "SH"); break;
      case "Щ": return пя("r", "SHH"); break;
      case "Ъ": return пя("r", "JQ"); break;
      case "Ы": return пя("r", "Y"); break;
      case "Ь": return пя("r", "Q"); break;
      case "Э": return пя("r", "EH"); break;
      case "Ю": return пя("r", "JU"); break;
      case "Я": return пя("r", "JA"); break;

      case "a": return пя("e", "a"); break;
      case "b": return пя("e", "b"); break;
      case "c": return пя("e", "c"); break;
      case "d": return пя("e", "d"); break;
      case "e": return пя("e", "e"); break;
      case "f": return пя("e", "f"); break;
      case "g": return пя("e", "g"); break;
      case "h": return пя("e", "h"); break;
      case "i": return пя("e", "i"); break;
      case "j": return пя("e", "jj"); break;
      case "k": return пя("e", "k"); break;
      case "l": return пя("e", "l"); break;
      case "m": return пя("e", "m"); break;
      case "n": return пя("e", "n"); break;
      case "o": return пя("e", "o"); break;
      case "p": return пя("e", "p"); break;
      case "q": return пя("e", "q"); break;
      case "r": return пя("e", "r"); break;
      case "s": return пя("e", "s"); break;
      case "t": return пя("e", "t"); break;
      case "u": return пя("e", "u"); break;
      case "v": return пя("e", "v"); break;
      case "w": return пя("e", "ww"); break;
      case "x": return пя("e", "xx"); break;
      case "y": return пя("e", "y"); break;
      case "z": return пя("e", "z"); break;
      case "A": return пя("e", "A"); break;
      case "B": return пя("e", "B"); break;
      case "C": return пя("e", "C"); break;
      case "D": return пя("e", "D"); break;
      case "E": return пя("e", "E"); break;
      case "F": return пя("e", "F"); break;
      case "G": return пя("e", "G"); break;
      case "H": return пя("e", "H"); break;
      case "I": return пя("e", "I"); break;
      case "J": return пя("e", "JJ"); break;
      case "K": return пя("e", "K"); break;
      case "L": return пя("e", "L"); break;
      case "M": return пя("e", "M"); break;
      case "N": return пя("e", "N"); break;
      case "O": return пя("e", "O"); break;
      case "P": return пя("e", "P"); break;
      case "Q": return пя("e", "Q"); break;
      case "R": return пя("e", "R"); break;
      case "S": return пя("e", "S"); break;
      case "T": return пя("e", "T"); break;
      case "U": return пя("e", "U"); break;
      case "V": return пя("e", "V"); break;
      case "W": return пя("e", "WW"); break;
      case "X": return пя("e", "XX"); break;
      case "Y": return пя("e", "Y"); break;
      case "Z": return пя("e", "Z"); break;
      default:
      {
        if (Буква.charCodeAt(0) > 255) {
          return "xu" + Буква.charCodeAt(0).toString(16) + "x";
        } else {
          return Буква;
        }
      }
    }
  }; // ПреобразуйБуквуВЛатиницу

  var Лат = "";
  /// з = ТекущийЯзык;
  var з = { знач: "r" };
  for (var ц = 0, цМакс = Рус.length; ц < цМакс; ц += 1) {
    Лат = Лат + ПреобразуйБуквуВЛатиницу(Рус.charAt(ц), з);
  }
  return Лат;
};

function ПреобразуйСтрокуИзЛатиницы(s) {
  // Оригинальный код для обратного преобразования написал Vladimir_Zaitsev, 
  // https://habr.com/ru/post/265455/ . В коде не было лицензии и это мы воспринимаем как
  // Public Domain. 
  // Если на входе - некорректная последовательность литер, то вызывается функция Ош.
  // В данной реализации она вставляет в текст литеру «^» в месте ошибки и пытается
  // продолжить разбор. 
  var slength = s.length;
  var sb = ''; // 
  var индексВРезультирующейСтроке = 0;
  var текущийЯзык = 0; // 0 - русский, 1 - английский

  var i = 0;
  // Дв элемента буфера, б == б
  var б = '';
  var следующаяБуква = '';

  function Чб() { // Читай-букву. Заполняет б,следующаяБуква
    if (i >= slength) {
      б = '';
    } else {
      б = s[i];
      i++;
      if (i == slength) {
        следующаяБуква = '';
      } else {
        следующаяБуква = s[i];
      }
    }
  }

  var Зб = function (бб) { // Запиши-букву
    sb = sb + бб;
    индексВРезультирующейСтроке++;
  }

  // Функция Ош вызывается, когда возникла проблема.
  // Она может бросать исключение, а может вставлять в текст
  // какую-нибудь крокозяблу и возвращаться. Вызывающий код
  // (должен быть) написан так, что ни при какой ошибке
  // не происходит зацикливания
  function ОшибкаБросающаяИсключение(Сообщ) {
    var ПолноеСообщ = Сообщ.toString() + " в позиции " + i;
    alert(ПолноеСообщ + "");
    throw ПолноеСообщ;
  }

  function ОшибкаВставляющаяКрокозяблу(Сообщ) {
    var ПолноеСообщ = Сообщ.toString() + " в позиции " + i;
    console.log(ПолноеСообщ + "");
    Зб('^');
  }

  function Ош(Сообщ) {
    ОшибкаВставляющаяКрокозяблу(Сообщ);
    // также можно использовать ОшибкаБросающаяИсключение(Сообщ);
  }

  function ProchitajjXU() {
    Чб();
    var КодЮникод = 0;
    var ПределРазмера = 6;
    while (true) {
      if (ПределРазмера < 0) {
        Ош("Слишком длинное 16-ричное число");
        Зб(б);
        return;
      }
      if (б == 'x') { // здесь case не подходит, т.к.
        // надо сделать break и выйти из цикла!
        Зб(String.fromCharCode(КодЮникод));
        break;
      }
      КодЮникод = КодЮникод * 16;
      switch (б) {
        // https://jsperf.com/switch-vs-object-function
        // Но не факт, что здесь не должен быть мап из букв в числа... 
        case '0': break;
        case '1': КодЮникод += 1; break;
        case '2': КодЮникод += 2; break;
        case '3': КодЮникод += 3; break;
        case '4': КодЮникод += 4; break;
        case '5': КодЮникод += 5; break;
        case '6': КодЮникод += 6; break;
        case '7': КодЮникод += 7; break;
        case '8': КодЮникод += 8; break;
        case '9': КодЮникод += 9; break;
        case 'A': case 'a': КодЮникод += 0xA; break;
        case 'B': case 'b': КодЮникод += 0xB; break;
        case 'C': case 'c': КодЮникод += 0xC; break;
        case 'D': case 'd': КодЮникод += 0xD; break;
        case 'E': case 'e': КодЮникод += 0xE; break;
        case 'F': case 'f': КодЮникод += 0xF; break;
        default: Ош("Неверная 16-ричная цифра"); Зб(б); return;
      }
      Чб(); 
      ПределРазмера--;
    } 
  }/*function ProchitajjXU*/

  Чб();

  while (б != '') {// Идем по строке слева направо. В принципе, подходит для обработки потока
    switch (текущийЯзык) {
      case 0/*Язык:русский*/:
        switch (следующаяБуква) {
          case 'H':
            var бб = б;
            Чб(); // Пропускаем постфикс
            var DveH = (следующаяБуква == 'H');
            switch (бб) {
              case 'Z': Зб('Ж'); break;
              case 'K': Зб('Х'); break;
              case 'C': Зб('Ч'); break;
              case 'S': if (DveH) { Зб('Щ'); Чб(); } else { Зб('Ш'); }; break;
              case 'E': Зб('Э'); break;
              default: Ош("Неверная конструкция ...H"); break;
            };
            break;
          case 'h':
            var бб = б;
            Чб(); // Пропускаем постфикс
            var Dveh = (следующаяБуква == 'h');
            switch (бб) {
              case 'z': Зб('ж'); break;
              case 'k': Зб('х'); break;
              case 'c': Зб('ч'); break;
              case 's': if (Dveh) { Зб('щ'); Чб(); } else { Зб('ш'); }; break;
              case 'e': Зб('э'); break;
              default: Ош("Неверная конструкция ...h"); break;
            };
            break;
          default: // префиксы и одиночные литеры
            switch (б) {
              case 'x': // xe, xu
                Чб();
                switch (б) {
                  case 'u': ProchitajjXU(); break;
                  case 'e': текущийЯзык = 1; break;
                  default: Ош("Неверная конструкция x..."); break;
                }; break;
              case 'w':
              case 'W':
                Ош("W и w неуместны в яролите"); break;
              case 'J': // Префикс
                Чб(); // пропускаем префикс
                switch (б) { // ojqua
                  case 'O': Зб('Ё'); break;
                  case 'J': Зб('Й'); break;
                  case 'Q': Зб('Ъ'); break;
                  case 'U': Зб('Ю'); break;
                  case 'A': Зб('Я'); break;
                  default: Ош("Неверная конструкция J..."); break;
                }; break;
              case 'j': // Префикс
                Чб(); // пропускаем префикс
                switch (б) { // ojqua
                  case 'o': Зб('ё'); break;
                  case 'j': Зб('й'); break;
                  case 'q': Зб('ъ'); break;
                  case 'u': Зб('ю'); break;
                  case 'a': Зб('я'); break;
                  default: Ош("Неверная конструкция j..."); break;
                }; break;
              case 'A': Зб('А'); break; case 'a': Зб('а'); break;
              case 'B': Зб('Б'); break; case 'b': Зб('б'); break;
              case 'V': Зб('В'); break; case 'v': Зб('в'); break;
              case 'G': Зб('Г'); break; case 'g': Зб('г'); break;
              case 'D': Зб('Д'); break; case 'd': Зб('д'); break;
              case 'E': Зб('Е'); break; case 'e': Зб('е'); break;
              case 'Z': Зб('З'); break; case 'z': Зб('з'); break;
              case 'I': Зб('И'); break; case 'i': Зб('и'); break;
              case 'K': Зб('К'); break; case 'k': Зб('к'); break;
              case 'L': Зб('Л'); break; case 'l': Зб('л'); break;
              case 'M': Зб('М'); break; case 'm': Зб('м'); break;
              case 'N': Зб('Н'); break; case 'n': Зб('н'); break;
              case 'O': Зб('О'); break; case 'o': Зб('о'); break;
              case 'P': Зб('П'); break; case 'p': Зб('п'); break;
              case 'R': Зб('Р'); break; case 'r': Зб('р'); break;
              case 'S': Зб('С'); break; case 's': Зб('с'); break;
              case 'T': Зб('Т'); break; case 't': Зб('т'); break;
              case 'U': Зб('У'); break; case 'u': Зб('у'); break;
              case 'F': Зб('Ф'); break; case 'f': Зб('ф'); break;
              case 'C': Зб('Ц'); break; case 'c': Зб('ц'); break; 
              case 'Y': Зб('Ы'); break; case 'y': Зб('ы'); break;
              case 'Q': Зб('Ь'); break; case 'q': Зб('ь'); break;
              case 'H': Ош("Неверная одиночная H"); break;
              case 'h': Ош("Неверная одиночная h"); break;
              default: Зб(б);
            }
        }
        break;
      case 1/*Язык:английский*/:
        switch (б) {
          case 'w': 
            Чб();
            switch (б) {
              case 'w': Зб('w'); break;
              default: Ош("Неверная конструкция w..."); break;
            }; break;
          case 'W': 
            Чб();
            switch (б) {
              case 'W': Зб('W'); break;
              default: Ош("Неверная конструкция W..."); break;
            }; break;
          case 'x':
            Чб();
            switch (б) {
              case 'x': Зб('x'); break;
              case 'r': текущийЯзык = 0; break;
              case 'u': ProchitajjXU(); break;
              default: Ош("Неверная конструкция x..."); break;
            }; break;
          case 'X':
            Чб();
            switch (б) {
              case 'X': Зб('X'); break;
              default: Ош("Неверная конструкция X..."); break;
            }; break;
          default: Зб(б); break;
        }; break;
      default: Ош("Неверный язык"); break;
    }
    Чб(); // переходим к следующей букве
  }
  return sb;
}                           

/* (C) Denis Budyak 2017-2019 
 
Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/ 
