function Person(firstName, lastName, birthday) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.nameToPresent = function () {
        return this.firstName + " " + this.lastName;
    }
}
// criação de um objeto do tipo Person
var firstPerson = new Person("Fulano", "de tal", "Rio de Sul");
// exemplo de chamada de um método
var nome = firstPerson.nameToPresent();