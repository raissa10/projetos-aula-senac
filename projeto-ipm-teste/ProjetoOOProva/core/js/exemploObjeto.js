function Person(firstName, lastName, birthday) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.nameToPresent = function () {
        return this.firstName + " " + this.lastName;
    }
}
// cria��o de um objeto do tipo Person
var firstPerson = new Person("Fulano", "de tal", "Rio de Sul");
// exemplo de chamada de um m�todo
var nome = firstPerson.nameToPresent();