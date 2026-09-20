import { FormControl } from "@angular/forms";

export interface SignUpForm {
    nameTH: FormControl<string | null>;
    surnameTH: FormControl<string | null>;
    nameEN: FormControl<string | null>;
    surnameEN: FormControl<string | null>;
    idCard: FormControl<string | null>;
    mobilePhone: FormControl<string | null>;
    telephone: FormControl<string | null>;
    brithDate: FormControl<string | null>;
    username: FormControl<string | null>;
    password: FormControl<string | null>;
    confirmPassword: FormControl<string | null>;
    roleCode: FormControl<string[] | null>;
    genderCode: FormControl<String | null>;
    createdBy: FormControl<string | null>;
    email: FormControl<string | null>;
}