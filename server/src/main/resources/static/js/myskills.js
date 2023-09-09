// 수정 Form에서 추가 및 삭제 내용을 저장하는 오브젝트 (Server에 보내짐)
const changedSkills = {
    'BackEnd': [],
    'FrontEnd': [],
    'DevOps': [],
    'ETC': [],
};
// 수정 Form에서 add, remove를 여려번 하면 위 changedSkills 활용이 에매해짐
// checkInitialSkill에서 초기 DB에서 가져온 값을 판별하여 DB에 없는 값은 remove 정보를 담지 않도록 함
let checkInitialSkill = []

// header.js와 연계되어 편집 버튼 toggle
function toggleEditMode() {
    const controls = document.querySelectorAll('.edit-controls');
    controls.forEach(control => {
        if (control.style.display === 'none') {
            control.style.display = 'block';
        } else {
            control.style.display = 'none';
        }
    });
}


// ====== 페이지, 모달창 랜더링 함수 ============================ //

// Server에서 Map<SkillType, List<String>> 타입을 JSON으로 변환 후, Model에 담아서 보냄
var allSkills = $('#allSkills').data('skills');  
/** allSkills 구조 예시 
 * {
 *   "BackEnd" : ["MongoDB", "Python", "Java"],
 *   "FrontEnd" : ["CSS3", "JavaScript", "JQuery"]
 * }
 */

// 1. 페이지 로드시 기술 스택 랜더링
window.addEventListener("load", function() {
    // 랜더링 해야 될 태그들을 가져옴
    var containers = {
        "BackEnd": document.querySelector("#backend-container"),
        "FrontEnd": document.querySelector("#frontend-container"),
        "DevOps": document.querySelector("#devops-container"),
        "ETC": document.querySelector("#etc-container")
    };

    // allSkills 객체를 순회하며 각 카테고리(예: BackEnd, FrontEnd 등)와 그에 해당하는 스킬들 처리
    for(var category in allSkills) {
        // 해당 카테고리의 스킬 리스트
        var skills = allSkills[category];

        // 해당 카테고리의 HTML 컨테이너 
        var container = containers[category];

        let imageCount = 0;

        // 각 스킬에 대해 새로운 <img> 태그를 생성하여 badge 화면에 랜더링
        skills.forEach(function(skill) {

            var imgTag = document.createElement("img");

            // <img> 태그의 src 속성을 badges 객체의 값으로 설정
            imgTag.src = badges[skill];

            // <img> 태그간 간격 조절
            imgTag.className = "me-1";
            
            // 생성된 <img> 태그를 해당 카테고리의 HTML 컨테이너에 추가
            container.appendChild(imgTag);

            imageCount++;

            // 5개의 이미지가 추가될 때마다 <br> 태그 삽입
            if (imageCount > 0 && imageCount % 5 === 0) {
                var breakTag = document.createElement("br");
                container.appendChild(breakTag);
            }
        });
    }
});

// 2. 수정 모달창 활성화 시 최초 페이지 요청 때 받은 DB 값으로 랜더링
document.getElementById('skillsAddForm').addEventListener('show.bs.modal', loadSkillsOnModal);

function loadSkillsOnModal() {
    for(var category in allSkills) {
        var skills = allSkills[category];

        skills.forEach(function(skill) {
            modalSetting(category, skill)
        });
    }
}

function modalSetting(section, value) {
    const skillsDiv = document.getElementById(`${section}Skills`);
    
    if (value) {
        const container = document.createElement("div");
        container.className = "no-hover-container";

        // 메인 버튼 생성
        const btn = document.createElement("button");
        btn.className = "btn btn-outline-secondary btn-sm m-1 d-flex align-items-center no-hover";

        // 레이블 추가
        const btnText = document.createElement("span");
        btnText.innerHTML = value;
        btn.appendChild(btnText);

        // x 표시 추가
        const closeIcon = document.createElement("span");
        closeIcon.innerHTML = "&times;";
        closeIcon.className = "close-icon";  // x 표시 스타일 적용
        closeIcon.style.marginLeft = "5px";
        closeIcon.style.cursor = "pointer";
        closeIcon.style.transition = "0.3s";

        // 삭제 버튼 클릭 시 콜백 함수 (랜더링 된 개별마다 따로 작동함)
        closeIcon.onclick = function(event) {
            event.stopPropagation();
            updateChangedSkills(section, value, 'removed');
            skillsDiv.removeChild(container);

            // 삭제된 skill을 selectBox의 option에 다시 추가하여 선택할 수 있게 함
            el = `
                <option id="${value}" value="${value}">${value}</option>
            `
            $(`#${section}Select`).append(el)
        };

        btn.appendChild(closeIcon);
        container.appendChild(btn);

        // 컨테이너에 버튼 추가
        skillsDiv.appendChild(container);
        // 추가된 skill을 selectBox의 option에서 없애어 가독성을 좋게 함
        $(`#${value}`).remove()
    }
}

// 위 내용에 따라 세팅된 모달창 활성화
function getUpdateForm(event){
    var modal = new bootstrap.Modal(document.getElementById("skillsAddForm"));
    modal.show();
}

// 모달 창이 닫힐 때 
document.addEventListener('DOMContentLoaded', (event) => {
    var myModal = document.getElementById('skillsAddForm')
    myModal.addEventListener('hidden.bs.modal', function (event) {
        // 배열 초기화
        changedSkills['BackEnd'] = [];
        changedSkills['FrontEnd'] = [];
        changedSkills['DevOps'] = [];
        changedSkills['ETC'] = [];

        checkInitialSkill = [];

        // 새로 추가 및 삭제 내역 / 선택한 내역 초기화
        document.getElementById('BackEndSkills').innerHTML = '';
        document.getElementById('FrontEndSkills').innerHTML = '';
        document.getElementById('DevOpsSkills').innerHTML = '';
        document.getElementById('ETCSkills').innerHTML = '';
    
        document.getElementById('BackEndSelect').selectedIndex = -1;
        document.getElementById('FrontEndSelect').selectedIndex = -1;
        document.getElementById('DevOpsSelect').selectedIndex = -1;
        document.getElementById('ETCSelect').selectedIndex = -1;
    });
});

// ====== 페이지, 모달창 랜더링 함수 ============================ //


// ====== select box에서 option 클릭 시 기술 랜더링 ============================ //

// select 태그 onchange 콜백 함수
function addSkills(section) {
    const selectBox = document.getElementById(`${section}Select`);
    const value = selectBox.value;  // 선택된 Skill 값

    const skillsDiv = document.getElementById(`${section}Skills`);

    // 동일한 스킬 2번 선택하지 못하게 확인
    const existingButtons = skillsDiv.querySelectorAll('.no-hover-container');
    let valueExists = false;
    existingButtons.forEach(button => {
        const label = button.querySelector('span:not(.close-icon)').innerText; 
        if (label === value) {
            valueExists = true;
        }
    });
    
    // 동일하지 않다면 SKill을 모달창에 추가
    if (value && !valueExists) {
        const container = document.createElement("div");
        container.className = "no-hover-container";
        
        // 메인 버튼 생성
        const btn = document.createElement("button");
        btn.className = "btn btn-outline-secondary btn-sm m-1 d-flex align-items-center no-hover";
    
        // 레이블 추가
        const btnText = document.createElement("span");
        btnText.innerHTML = value;
        btn.appendChild(btnText);
    
        // x 표시 추가
        const closeIcon = document.createElement("span");
        closeIcon.innerHTML = "&times;";
        closeIcon.className = "close-icon";  
        closeIcon.style.marginLeft = "5px";
        closeIcon.style.cursor = "pointer";
        closeIcon.style.transition = "0.3s"; 
    
        // 새로 추가한 버튼의 x 표시 클릭시 콜백 함수 (개별적으로 작동)
        closeIcon.onclick = function(event) {
            event.stopPropagation();

            updateChangedSkills(section, value, 'removed');
            skillsDiv.removeChild(container);

            // 삭제 된 이후, selectbox의 option에서 다시 선택할 수 있게 추가
            el = `
                <option id="${value}" value="${value}">${value}</option>
            `
            $(`#${section}Select`).append(el)
        };

        btn.appendChild(closeIcon);
        container.appendChild(btn);
    
        // 컨테이너에 버튼 추가
        updateChangedSkills(section, value, 'added');
        skillsDiv.appendChild(container);

        // 추가 된 이후, selectbox의 option에서 사라지게 하여 가독성 추가
        $(`#${value}`).remove()
    }
}

// ====== select box에서 option 클릭 시 기술 랜더링 ============================ //


// ====== 수정하기 버튼 클릭 시 Server와 통신하는 함수 ============================ //

function updateSkills() {
    const jwtToken = localStorage.getItem('jwtToken'); 

    $.ajax({
        url: "/auth/skills",
        type: "POST",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(changedSkills),
        headers: {
            'Authorization': jwtToken  
        },

        success: function(response) {
            location.reload(true);
        },
        error: function(error) {
            alert(error.responseJSON.data);
        }
    });
}

// ====== 수정하기 버튼 클릭 시 Server와 통신하는 함수 ============================ //


// ====== 서버에 보내는 객체의 유효성을 체크하는 함수 ============================ //

// (add, remove를 반복해서 한다면, 마지막 액션만 기록되어 Server로 보내져야함)
function updateChangedSkills(section, name, status) {
    // changedSkills의 section 키에 연결된 배열에서 name이 같은 객체를 찾음
    // existing에는 배열에서 찾은 객체 저장 / 찾지 못했다면 undefined 저장
    const existing = changedSkills[section].find(skill => skill.name === name);
    
    // 이전에 추가되거나 삭제된 기록이 있다면, 그 기록을 지움
    if (existing) {
      const index = changedSkills[section].indexOf(existing);
      changedSkills[section].splice(index, 1);
    }

    // DB에서 가져온 상태에서 처음 removed 되었다면, 정보를 checkInitialSkill 배열에 추가
    if (status === 'removed' && !existing) {
        checkInitialSkill.push({ section, name });
    } 

    // 이전이 added 상태였고, 현재 'removed' 된거라면 -> DB에 없던 상태에서 added된건지 확인
    if (status === 'removed' && existing && existing.status === 'added') {
        const initialCheck1 = checkInitialSkill.find(skill => skill.name === name && skill.section === section);
        // 첫 요청 시 DB에 없던 상태에서 추가된 것이 아니라면 removed를 push하지 않고 함수 종료 
        // (DB에 없는 상태이니 removed를 Server로 보내면 안됨)
        if (!initialCheck1) {
            return;
        }
    }

    // 반대의 상황도 처리
    if (status ==='added' && existing && existing.status === 'removed'){
        const initialCheck2 = checkInitialSkill.find(skill => skill.name === name && skill.section === section);

        if (initialCheck2) {
            return;
        }
    }

    // 새로운 상태를 changedSkills에 추가 
    changedSkills[section].push({ name, status });  
}

// ====== 서버에 보내는 객체의 유효성을 체크하는 함수 ============================ //


// seletbox에서 선택할 수 있는 badges (해당 오브젝트로 랜더링)
var badges = {
    // BackEnd
    "Python": "https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=Python&logoColor=white",
    "Java": "https://img.shields.io/badge/Java-007396.svg?style=flat-square&logo=openjdk&logoColor=white",
    "SpringBoot":"https://img.shields.io/badge/SpringBoot-%236DB33F.svg?style=flat-square&logo=spring&logoColor=white",
    "SpringSecurity":"https://img.shields.io/badge/Spring_Security-%236DB33F?style=flat-square&logo=springsecurity&logoColor=white",
    "Mybatis":"https://img.shields.io/badge/MyBatis-0052CC.svg?style=flat-square&logo=FamPay&logoColor=white",
    "Jpa":"https://img.shields.io/badge/JPA-5F5F5F?style=flat-square&logo=buffer&logoColor=white",

    "MySQL":"https://img.shields.io/badge/MySQL-4479A1.svg?style=flat-square&logo=MySQL&logoColor=white",
    "MongoDB":"https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=MongoDB&logoColor=white",

    "JUnit5":"https://img.shields.io/badge/JUnit5-25A162?style=flat-square&logo=junit5&logoColor=white",
    "Postman":"https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white",

    // FrontEnd
    "HTML5":"https://img.shields.io/badge/HTML5-%23E34F26.svg?style=flat-square&logo=html5&logoColor=white",
    "CSS3":"https://img.shields.io/badge/CSS3-%231572B6.svg?style=flat-square&logo=css3&logoColor=white",
    "JavaScript":"https://img.shields.io/badge/JavaScript-%23323330.svg?style=flat-square&logo=javascript&logoColor=%23F7DF1E",
    "JQuery":"https://img.shields.io/badge/jQuery-%230769AD.svg?style=flat-square&logo=jquery&logoColor=white",
    "BootStrap":"https://img.shields.io/badge/bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white",

    // DevOps
    "Docker":"https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white",
    "Kubernetes":"https://img.shields.io/badge/Kubernetes-2496ED?style=flat-square&logo=Kubernetes&logoColor=white",
    "AmazonEC2":"https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=amazonec2&logoColor=white",

    // ETC
    "GitHub":"https://img.shields.io/badge/GitHub-%23121011.svg?style=flat-square&logo=github&logoColor=white",
    "Notion":"https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white",
    "Jira":"https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=Jira&logoColor=white",
    "VisualStudioCode":"https://img.shields.io/badge/VisualStudioCode-007ACC?style=flat-square&logo=VisualStudioCode&logoColor=white",
    "Eclipse":"https://img.shields.io/badge/Eclipse-2C2255?style=flat-square&logo=Eclipse&logoColor=white",
    "AWS":"https://img.shields.io/badge/AWS-%23FF9900.svg?style=flat-square&logo=amazon-aws&logoColor=white",
    "Firebase":"https://img.shields.io/badge/Firebase-FF6A00?style=flat-square&logo=firebase&logoColor=white",
    "AmazonS3":"https://img.shields.io/badge/AmazonS3-569A31?style=flat-square&logo=amazons3&logoColor=white"
};



