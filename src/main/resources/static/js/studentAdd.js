function readURL(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function (e) {
            $('#image').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]); // convert to base64 string
    }
}

//display selected image
$("#imgInput").change(function () {
    readURL(this);
});
//check attempt and set value relevant filed
//OL result
//subject one
$("#subOLOneAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLOneIndex").val(olOneIndexNumber);
        $("#subOLOneSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLOneIndex").val(olTwoIndexNumber);
        $("#subOLOneSubYear").val(olTwoYear);
    }
});
//subject two
$("#subOLTwoAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLTwoIndex").val(olOneIndexNumber);
        $("#subOLTwoSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLTwoIndex").val(olTwoIndexNumber);
        $("#subOLTwoSubYear").val(olTwoYear);
    }
});
//subject three
$("#subOLThreeAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLThreeIndex").val(olOneIndexNumber);
        $("#subOLThreeSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLThreeIndex").val(olTwoIndexNumber);
        $("#subOLThreeSubYear").val(olTwoYear);
    }
});
//subject four
$("#subOLFourAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLFourIndex").val(olOneIndexNumber);
        $("#subOLFourSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLFourIndex").val(olTwoIndexNumber);
        $("#subOLFourSubYear").val(olTwoYear);
    }
});
//subject five
$("#subOLFiveAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLFiveIndex").val(olOneIndexNumber);
        $("#subOLFiveSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLFiveIndex").val(olTwoIndexNumber);
        $("#subOLFiveSubYear").val(olTwoYear);
    }
});
//subject six
$("#subOLSixAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLSixIndex").val(olOneIndexNumber);
        $("#subOLSixSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLSixIndex").val(olTwoIndexNumber);
        $("#subOLSixSubYear").val(olTwoYear);
    }
});
//subject seven
$("#subOLSevenAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLSevenIndex").val(olOneIndexNumber);
        $("#subOLSevenSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLSevenIndex").val(olTwoIndexNumber);
        $("#subOLSevenSubYear").val(olTwoYear);
    }
});
//subject eight
$("#subOLEightAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLEightIndex").val(olOneIndexNumber);
        $("#subOLEightSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLEightIndex").val(olTwoIndexNumber);
        $("#subOLEightSubYear").val(olTwoYear);
    }
});
//subject nine
$("#subOLNineAttempt").bind("change", function () {

    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLNineIndex").val(olOneIndexNumber);
        $("#subOLNineSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLNineIndex").val(olTwoIndexNumber);
        $("#subOLNineSubYear").val(olTwoYear);
    }
});
//subject ten
$("#subOLTenAttempt").bind("change", function () {
    let olOneIndexNumber = $("#indexOneOL").val();
    let olOneYear = $("#yearOneOL").val();
    let olTwoIndexNumber = $("#indexTwoOL").val();
    let olTwoYear = $("#yearTwoOL").val();
    if ($(this).val() === 'FIRST') {
        if (olOneIndexNumber.length === 0 || olOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLTenIndex").val(olOneIndexNumber);
        $("#subOLTenSubYear").val(olOneYear);
    } else {
        if (olTwoIndexNumber.length === 0 || olTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subOLTenIndex").val(olTwoIndexNumber);
        $("#subOLTenSubYear").val(olTwoYear);
    }
});

//AL result
//subject one
$("#subALOneAttempt").bind("change", function () {
    let alOneIndexNumber = $("#indexOneAL").val();
    let alOneYear = $("#yearOneAL").val();
    let alTwoIndexNumber = $("#indexTwoAL").val();
    let alTwoYear = $("#yearTwoAL").val();
    if ($(this).val() === 'FIRST') {
        if (alOneIndexNumber.length === 0 || alOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALOneIndex").val(alOneIndexNumber);
        $("#subALOneSubYear").val(alOneYear);
    } else {
        if (alTwoIndexNumber.length === 0 || alTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Advance Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALOneIndex").val(alTwoIndexNumber);
        $("#subALOneSubYear").val(alTwoYear);
    }
});
//subject two
$("#subALTwoAttempt").bind("change", function () {
    let alOneIndexNumber = $("#indexOneAL").val();
    let alOneYear = $("#yearOneAL").val();
    let alTwoIndexNumber = $("#indexTwoAL").val();
    let alTwoYear = $("#yearTwoAL").val();
    if ($(this).val() === 'FIRST') {
        if (alOneIndexNumber.length === 0 || alOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALTwoIndex").val(alOneIndexNumber);
        $("#subALTwoSubYear").val(alOneYear);
    } else {
        if (alTwoIndexNumber.length === 0 || alTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Advance Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALTwoIndex").val(alTwoIndexNumber);
        $("#subALTwoSubYear").val(alTwoYear);
    }
});
//subject three
$("#subALThreeAttempt").bind("change", function () {
    let alOneIndexNumber = $("#indexOneAL").val();
    let alOneYear = $("#yearOneAL").val();
    let alTwoIndexNumber = $("#indexTwoAL").val();
    let alTwoYear = $("#yearTwoAL").val();
    if ($(this).val() === 'FIRST') {
        if (alOneIndexNumber.length === 0 || alOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALThreeIndex").val(alOneIndexNumber);
        $("#subALThreeSubYear").val(alOneYear);
    } else {
        if (alTwoIndexNumber.length === 0 || alTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Advance Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALThreeIndex").val(alTwoIndexNumber);
        $("#subALThreeSubYear").val(alTwoYear);
    }
});
//subject four
$("#subALFourAttempt").bind("change", function () {
    let alOneIndexNumber = $("#indexOneAL").val();
    let alOneYear = $("#yearOneAL").val();
    let alTwoIndexNumber = $("#indexTwoAL").val();
    let alTwoYear = $("#yearTwoAL").val();
    if ($(this).val() === 'FIRST') {
        if (alOneIndexNumber.length === 0 || alOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALFourIndex").val(alOneIndexNumber);
        $("#subALFourSubYear").val(alOneYear);
    } else {
        if (alTwoIndexNumber.length === 0 || alTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Advance Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALFourIndex").val(alTwoIndexNumber);
        $("#subALFourSubYear").val(alTwoYear);
    }
});
//subject five
$("#subALFiveAttempt").bind("change", function () {
    let alOneIndexNumber = $("#indexOneAL").val();
    let alOneYear = $("#yearOneAL").val();
    let alTwoIndexNumber = $("#indexTwoAL").val();
    let alTwoYear = $("#yearTwoAL").val();
    if ($(this).val() === 'FIRST') {
        if (alOneIndexNumber.length === 0 || alOneYear.length === 0) {
            swal({
                title: "Could You Please Check Ordinary Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALFiveIndex").val(alOneIndexNumber);
        $("#subALFiveSubYear").val(alOneYear);
    } else {
        if (alTwoIndexNumber.length === 0 || alTwoYear.length === 0) {
            swal({
                title: "Could You Please Check Advance Level Second Attempt Index Number and Year",
                icon: "warning",
                text: "Please Enter Second Attempt Index Number and Year !",
            });
        }
        $("#subALFiveIndex").val(alTwoIndexNumber);
        $("#subALFiveSubYear").val(alTwoYear);
    }
});