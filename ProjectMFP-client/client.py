from transact_invoker import TransactInvoker

def print_obj(className, obj):
    print()
    if className == "car":
        print("ID: ", obj['id'])
        print("Model: ", obj['model'])
        print("Motor Fuel: ", obj['motorFuel'])
    elif className == "truck":
        print("ID: ", obj['id'])
        print("Model: ", obj['model'])
        print("Tonnage: ", obj['tonnage'])
    print()

def print_cars(carList):
    for car in carList:
        print_obj("car", car)
        
def print_trucks(truckList):
    for truck in truckList:
        print_obj("truck", truck)

def delete_car(id, invoker):
    return invoker.delete_obj(id, "car")

def delete_truck(id, invoker):
    return invoker.delete_obj(id, "truck")

def create_car(params, invoker):
    return invoker.save_obj("car", params)

def create_truck(params, invoker):
    return invoker.save_obj("truck", params)

def client_menu(invoker):
    print("Transact MFPC")
    print("1. Create car")
    print("2. Get all cars")
    print("3. Delete Car")
    print("4. Create truck")
    print("5. Get all trucks")
    print("6. Delete truck")
    print("0. Exit")
    option = int(input("Choose option: "))

    if option == 1:
        param1 = input("Model: ")
        param2 = input("Motor Fuel: ")
        params = param1 + "," + param2
        retId = create_car(params, invoker)
        print(retId.text)
    elif option == 2:
        carList = invoker.get_cars()
        print_cars(carList)
    elif option == 3:
        id = input("ID: ")
        retId = delete_car(id, invoker)
        print(retId.text)
    elif option == 4:
        param1 = input("Tonnage: ")
        param2 = input("Model: ")
        params = param1 + "," + param2
        retId = create_truck(params, invoker)
        print(retId.text)
    elif option == 5:
        truckList = invoker.get_trucks()
        print_trucks(truckList)
    elif option == 6:
        id = input("ID: ")
        retId = delete_truck(id, invoker)
        print(retId.text)
    elif option == 0:
        exit(0)
    

if __name__ == '__main__':
    invoker = TransactInvoker()

    while True:
        client_menu(invoker)
