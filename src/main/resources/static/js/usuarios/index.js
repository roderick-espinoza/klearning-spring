/*
 * A diferencia del resto de CRUD, aqui el rol no es un select simple sino uno
 * multiple sobre la tabla usuarios_roles. Ese select lo gobierna Tom Select, que
 * lo pinta como un desplegable con chips: por eso marcar opcion.selected a mano
 * ya no sirve, hay que avisar al widget con setValue().
 *
 * La contraseña tampoco se precarga nunca: dejarla vacia al editar significa
 * "no cambiarla", y asi lo interpreta UsuarioServiceImpl.
 */

let tomRoles;

document.addEventListener('DOMContentLoaded', function () {

    // El modal ya esta en el DOM desde el arranque, asi que se instancia una
    // sola vez y no cada vez que se abre.
    tomRoles = new TomSelect('#selectRoles', {
        plugins: ['remove_button'],
        placeholder: 'Selecciona uno o más roles',
        maxItems: null,
        hideSelected: false,
        closeAfterSelect: false
    });

    // Sustituye al required que el navegador ya no puede validar sobre un
    // select oculto. El servidor lo sigue exigiendo con @NotEmpty.
    document.getElementById('formUsuario').addEventListener('submit', function (e) {
        if (tomRoles.getValue().length === 0) {
            e.preventDefault();

            Swal.fire({
                title: 'Falta asignar un rol',
                text: 'Selecciona al menos un rol para el usuario.',
                icon: 'warning',
                confirmButtonColor: '#0d6efd'
            });
        }
    });
});

function abrirModalNuevo() {
    document.getElementById('modalUsuarioTitulo').textContent = 'Nuevo Usuario';
    document.getElementById('formUsuario').action = URL_USUARIOS;

    document.getElementById('selectPersona').value = '';
    document.getElementById('inputUsuario').value = '';

    const password = document.getElementById('inputPassword');
    password.value = '';
    password.required = true;
    document.getElementById('ayudaPassword').textContent = 'Mínimo 8 caracteres.';

    marcarRoles([]);
}

function abrirModalEditar(btn) {
    document.getElementById('modalUsuarioTitulo').textContent = 'Editar Usuario';
    document.getElementById('formUsuario').action = URL_USUARIOS + '/' + btn.dataset.id;

    document.getElementById('selectPersona').value = btn.dataset.persona;
    document.getElementById('inputUsuario').value = btn.dataset.usuario;

    const password = document.getElementById('inputPassword');
    password.value = '';
    password.required = false;
    document.getElementById('ayudaPassword').textContent =
        'Déjala en blanco para mantener la contraseña actual.';

    const idsRoles = (btn.dataset.roles || '')
        .split(',')
        .map(id => id.trim())
        .filter(id => id.length > 0);

    marcarRoles(idsRoles);
}

function marcarRoles(idsRoles) {
    // El segundo argumento es "silent": evita disparar el change y con el las
    // validaciones que Tom Select encadena al abrir el modal.
    tomRoles.setValue(idsRoles, true);
}

function confirmarEliminar(e) {
    e.preventDefault();
    const form = e.target;

    Swal.fire({
        title: '¿Eliminar usuario?',
        text: 'El registro quedará marcado como eliminado.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then(result => {
        if (result.isConfirmed) form.submit();
    });

    return false;
}
