for (key in arr) { // NOK
  print(arr[key]);
  print(arr[key]);
}

for (name in object) { // OK
}

for (name in object) // OK
  if (object.hasOwnProperty(name)) {
    print(object[name]);
  }

for (name in object) { // OK
  if (!object.hasOwnProperty(name)) {
    continue;
  }
  print(object[name]);
}

for (key in arr) { // NOK
  function f() {}
  print(arr[key]);
}

for (key in obj)   // NOK
    a[key] = b[key];
