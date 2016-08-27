/* Generated By:JavaCC: Do not edit this line. compiladorConstants.java */
package proyecto1compi2;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface compiladorConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int entero = 6;
  /** RegularExpression Id. */
  int doble = 7;
  /** RegularExpression Id. */
  int booleano = 8;
  /** RegularExpression Id. */
  int caracter = 9;
  /** RegularExpression Id. */
  int cadena = 10;
  /** RegularExpression Id. */
  int comentario = 11;
  /** RegularExpression Id. */
  int comentarioall = 12;
  /** RegularExpression Id. */
  int dolar = 13;
  /** RegularExpression Id. */
  int sigabrir = 14;
  /** RegularExpression Id. */
  int sigcerrar = 15;
  /** RegularExpression Id. */
  int tentero = 16;
  /** RegularExpression Id. */
  int tdoble = 17;
  /** RegularExpression Id. */
  int tcaracter = 18;
  /** RegularExpression Id. */
  int tboolean = 19;
  /** RegularExpression Id. */
  int tcadena = 20;
  /** RegularExpression Id. */
  int igualacion = 21;
  /** RegularExpression Id. */
  int diferenciacion = 22;
  /** RegularExpression Id. */
  int menorque = 23;
  /** RegularExpression Id. */
  int menorigualque = 24;
  /** RegularExpression Id. */
  int mayorque = 25;
  /** RegularExpression Id. */
  int mayorigualque = 26;
  /** RegularExpression Id. */
  int nulo = 27;
  /** RegularExpression Id. */
  int or = 28;
  /** RegularExpression Id. */
  int and = 29;
  /** RegularExpression Id. */
  int nand = 30;
  /** RegularExpression Id. */
  int nor = 31;
  /** RegularExpression Id. */
  int xor = 32;
  /** RegularExpression Id. */
  int not = 33;
  /** RegularExpression Id. */
  int mas = 34;
  /** RegularExpression Id. */
  int menos = 35;
  /** RegularExpression Id. */
  int por = 36;
  /** RegularExpression Id. */
  int div = 37;
  /** RegularExpression Id. */
  int potencia = 38;
  /** RegularExpression Id. */
  int lienzo = 39;
  /** RegularExpression Id. */
  int extiende = 40;
  /** RegularExpression Id. */
  int conservar = 41;
  /** RegularExpression Id. */
  int coma = 42;
  /** RegularExpression Id. */
  int igual = 43;
  /** RegularExpression Id. */
  int publico = 44;
  /** RegularExpression Id. */
  int privado = 45;
  /** RegularExpression Id. */
  int protegido = 46;
  /** RegularExpression Id. */
  int var = 47;
  /** RegularExpression Id. */
  int aumento = 48;
  /** RegularExpression Id. */
  int decremento = 49;
  /** RegularExpression Id. */
  int sumasim = 50;
  /** RegularExpression Id. */
  int restasim = 51;
  /** RegularExpression Id. */
  int arreglo = 52;
  /** RegularExpression Id. */
  int corchetei = 53;
  /** RegularExpression Id. */
  int corcheted = 54;
  /** RegularExpression Id. */
  int parenti = 55;
  /** RegularExpression Id. */
  int parentd = 56;
  /** RegularExpression Id. */
  int llavei = 57;
  /** RegularExpression Id. */
  int llaved = 58;
  /** RegularExpression Id. */
  int si = 59;
  /** RegularExpression Id. */
  int sino = 60;
  /** RegularExpression Id. */
  int comprobar = 61;
  /** RegularExpression Id. */
  int caso = 62;
  /** RegularExpression Id. */
  int tvalor = 63;
  /** RegularExpression Id. */
  int dospuntos = 64;
  /** RegularExpression Id. */
  int salir = 65;
  /** RegularExpression Id. */
  int defecto = 66;
  /** RegularExpression Id. */
  int para = 67;
  /** RegularExpression Id. */
  int puntoycoma = 68;
  /** RegularExpression Id. */
  int mientras = 69;
  /** RegularExpression Id. */
  int hacer = 70;
  /** RegularExpression Id. */
  int continuar = 71;
  /** RegularExpression Id. */
  int pintarP = 72;
  /** RegularExpression Id. */
  int pintarOR = 73;
  /** RegularExpression Id. */
  int pintarS = 74;
  /** RegularExpression Id. */
  int principal = 75;
  /** RegularExpression Id. */
  int ordenar = 76;
  /** RegularExpression Id. */
  int sumarizar = 77;
  /** RegularExpression Id. */
  int ascendente = 78;
  /** RegularExpression Id. */
  int descendente = 79;
  /** RegularExpression Id. */
  int pares = 80;
  /** RegularExpression Id. */
  int impares = 81;
  /** RegularExpression Id. */
  int primos = 82;
  /** RegularExpression Id. */
  int id = 83;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\r\\n\"",
    "\"\\t\"",
    "<entero>",
    "<doble>",
    "<booleano>",
    "<caracter>",
    "<cadena>",
    "<comentario>",
    "<comentarioall>",
    "\"$\"",
    "<sigabrir>",
    "\"?\"",
    "<tentero>",
    "<tdoble>",
    "<tcaracter>",
    "<tboolean>",
    "<tcadena>",
    "\"==\"",
    "\"!=\"",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "\"!&\\u00c2\\u00a1\"",
    "\"||\"",
    "\"&&\"",
    "\"!&&\"",
    "\"!||\"",
    "\"&|\"",
    "\"!\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"^\"",
    "<lienzo>",
    "<extiende>",
    "<conservar>",
    "\",\"",
    "\"=\"",
    "<publico>",
    "<privado>",
    "<protegido>",
    "<var>",
    "\"++\"",
    "\"--\"",
    "\"+=\"",
    "\"-=\"",
    "<arreglo>",
    "\"[\"",
    "\"]\"",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "<si>",
    "<sino>",
    "<comprobar>",
    "<caso>",
    "<tvalor>",
    "\":\"",
    "<salir>",
    "<defecto>",
    "<para>",
    "\";\"",
    "<mientras>",
    "<hacer>",
    "<continuar>",
    "<pintarP>",
    "<pintarOR>",
    "<pintarS>",
    "<principal>",
    "<ordenar>",
    "<sumarizar>",
    "<ascendente>",
    "<descendente>",
    "<pares>",
    "<impares>",
    "<primos>",
    "<id>",
  };

}
